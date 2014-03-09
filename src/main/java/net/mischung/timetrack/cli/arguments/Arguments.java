package net.mischung.timetrack.cli.arguments;

import net.mischung.timetrack.cli.validation.Conjugation;
import net.mischung.timetrack.cli.validation.Constraint;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Arguments {



    private final Spec spec;
    private final List<String> arguments;
    private boolean valid;

    public Arguments(Spec spec, List<String> arguments) {
        this.spec = spec;
        this.arguments = arguments;
        this.valid = doValidate();
    }

    public static SpecFactory specify() {
        return new SpecFactory();
    }

    public Date getDate(String name) {
        DateArgument argument = (DateArgument) getSpecArgument(name);
        return argument.access(arguments);
    }

    public File getFile(String name) {
        FileArgument argument = (FileArgument) getSpecArgument(name);
        return argument.access(arguments);
    }

    public List<String> getRestArguments(String name) {
        RestArguments argument = (RestArguments) getSpecArgument(name);
        return argument.access(arguments);
    }

    public boolean isValid() {
        return valid;
    }

    private Argument getSpecArgument(String name) {
        ensureValid();
        return spec.getArgument(name);
    }

    private void ensureValid() {
        if (!valid) {
            throw new IllegalStateException();
        }
    }

    private boolean doValidate() {
        return new Conjugation(collectConstraints()).validate(arguments);
    }

    private Constraint[] collectConstraints() {
        Constraint[] constraints = new Constraint[spec.argumentsSize()];

        for (int i = 0; i < constraints.length; i++) {
            constraints[i] = spec.getArgumentAt(i).getConstraint();
        }

        return constraints;
    }

}
