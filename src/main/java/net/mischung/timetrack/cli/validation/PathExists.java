package net.mischung.timetrack.cli.validation;

import java.io.File;
import java.util.List;

public class PathExists implements Constraint {

    private final ListAccessor<String> accessor;

    public PathExists(ListAccessor<String> attributeAccessor) {
        this.accessor = attributeAccessor;
    }

    @Override
    public boolean validate(List<String> arguments) {
        return accessor.canAccess(arguments)
                && new File(accessor.get(arguments)).exists();
    }

}
