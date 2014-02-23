package net.mischung.timetrack.cli.validation;

import java.io.File;

public class PathExists implements Constraint {

    private final GuardedAccessor<String> accessor;

    public PathExists(GuardedAccessor<String> attributeAccessor) {
        this.accessor = attributeAccessor;
    }

    @Override
    public boolean validate() {
        return accessor.canAccess() && new File(accessor.get()).exists();
    }

}
