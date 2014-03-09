package net.mischung.timetrack.cli.arguments;

import net.mischung.timetrack.cli.validation.Conjugation;
import net.mischung.timetrack.cli.validation.Constraint;

import java.io.File;
import java.util.*;

public class Arguments {

    public static class Spec {

        private final List<Argument> arguments;
        private final Map<String, Integer> argMap;

        private Spec() {
            this.arguments = new LinkedList<Argument>();
            this.argMap = new HashMap<String, Integer>();
        }

        public Spec file(String name) {
            addArgument(new FileArgument(name, arguments.size()));
            return this;
        }

        public Spec date(String name) {
            addArgument(new DateArgument(name, arguments.size()));
            return this;
        }

        public Spec restArguments(String name) {
            addArgument(new RestArguments(name, arguments.size()));
            return this;
        }

        public List<Argument> getArguments() {
            return arguments;
        }

        public Argument getArgumentAt(int index) {
            return arguments.get(index);
        }

        public int argumentsSize() {
            return arguments.size();
        }

        private Argument getArgument(String name) {
            return arguments.get(argMap.get(name));
        }

        private void addArgument(Argument argument) {
            arguments.add(argument);
            argMap.put(argument.getName(), argument.getIndex());
        }

    }

    private final Spec spec;
    private final List<String> arguments;
    private boolean valid;

    public Arguments(Spec spec, List<String> arguments) {
        this.spec = spec;
        this.arguments = arguments;
        this.valid = doValidate();
    }

    public static Spec specify() {
        return new Spec();
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
