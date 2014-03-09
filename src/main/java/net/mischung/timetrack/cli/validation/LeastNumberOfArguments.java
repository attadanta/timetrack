package net.mischung.timetrack.cli.validation;

import java.util.List;

public class LeastNumberOfArguments implements Constraint {

    private final int number;

    public LeastNumberOfArguments(int number) {
        this.number = number;
    }

    @Override
    public boolean validate(List<String> arguments) {
        return arguments.size() >= number;
    }

}
