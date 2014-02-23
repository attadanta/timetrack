package net.mischung.timetrack.cli.validation;

import java.text.DateFormat;
import java.text.ParseException;

public class MatchesDateFormat implements Constraint {

    private final GuardedAccessor<String> accessor;
    private final DateFormat dateFormat;

    public MatchesDateFormat(GuardedAccessor<String> attributeAccessor, DateFormat dateFormat) {
        this.accessor = attributeAccessor;
        this.dateFormat = dateFormat;
    }

    @Override
    public boolean validate() {
        try {
            return accessor.canAccess() && dateFormat.parse(accessor.get()) != null;
        } catch (ParseException e) {
            return false;
        }
    }

}
