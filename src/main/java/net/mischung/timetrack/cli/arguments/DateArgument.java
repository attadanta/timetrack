package net.mischung.timetrack.cli.arguments;

import net.mischung.timetrack.cli.validation.Constraint;
import net.mischung.timetrack.cli.validation.MatchesDateFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateArgument extends Argument<Date> {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DateArgument(String name, int index) {
        super(name, index);
    }

    @Override
    public Constraint getConstraint() {
        return new MatchesDateFormat(accessor, dateFormat);
    }

    @Override
    public Date access(List<String> arguments) {
        try {
            return dateFormat.parse(accessor.get(arguments));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
