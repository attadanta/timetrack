package net.mischung.timetrack.cli.arguments;

import net.mischung.timetrack.cli.validation.Constraint;
import net.mischung.timetrack.cli.validation.LeastNumberOfArguments;

import java.util.List;

public class RestArguments extends Argument<List<String>> {

    public RestArguments(String name, int index) {
        super(name, index);
    }

    @Override
    public Constraint getConstraint() {
        return new LeastNumberOfArguments(index);
    }

    @Override
    public List<String> access(List<String> arguments) {
        return arguments.subList(index, arguments.size());
    }

}
