package net.mischung.timetrack.cli.validation;

import java.util.Collection;

public class LeastNumberOfArguments implements Constraint {

    private final int number;
    private final Collection<String> arguments;

    public LeastNumberOfArguments(int number, Collection<String> args) {
        this.number = number;
        this.arguments = args;
    }

    @Override
    public boolean validate() {
        return arguments.size() >= number;
    }

}
