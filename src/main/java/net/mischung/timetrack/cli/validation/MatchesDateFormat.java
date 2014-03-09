package net.mischung.timetrack.cli.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

public class MatchesDateFormat implements Constraint {

    private final ListAccessor<String> accessor;
    private final DateFormat dateFormat;

    public MatchesDateFormat(ListAccessor<String> listAccessor, DateFormat dateFormat) {
        this.accessor = listAccessor;
        this.dateFormat = dateFormat;
    }

    @Override
    public boolean validate(List<String> arguments) {
        try {
            return accessor.canAccess(arguments)
                    && dateFormat.parse(accessor.get(arguments)) != null;
        } catch (ParseException e) {
            return false;
        }
    }

}
