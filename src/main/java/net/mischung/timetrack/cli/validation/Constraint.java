package net.mischung.timetrack.cli.validation;

import java.util.List;

public interface Constraint {

    public boolean validate(List<String> arguments);

}
