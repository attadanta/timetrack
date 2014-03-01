package net.mischung.timetrack.sql;

import net.mischung.timetrack.sql.statements.AndFragment;
import net.mischung.timetrack.sql.statements.BetweenFragment;
import net.mischung.timetrack.sql.statements.NotInFragment;
import net.mischung.timetrack.sql.statements.SimpleSQLFragment;

import java.util.*;

public class ActivitiesSelector {

    private static final String selectFragment =
            "SELECT facts.id, activity_name, category_name, description, start_time, end_time FROM\n" +
                    "(SELECT activities.id as activity_id,\n" +
                    "        activities.name as activity_name,\n" +
                    "        categories.name as category_name\n" +
                    "    FROM activities\n" +
                    "    JOIN categories ON (activities.category_id = categories.id)\n" +
                    ") AS activities_categories\n" +
                    "JOIN facts ON (activities_categories.activity_id = facts.activity_id)";

    private final Date date;
    private final List<String> ignoredCategories;

    public ActivitiesSelector(Date date, Collection<String> ignoredCategories) {
        this.date = date;

        this.ignoredCategories = new ArrayList<String>();
        this.ignoredCategories.addAll(ignoredCategories != null ? ignoredCategories : Collections.<String>emptyList());
    }

    boolean isDateGiven() {
        return date != null;
    }

    boolean areIgnoredCasesGiven() {
        return !ignoredCategories.isEmpty();
    }

    ResultSchema getResultSchema() {
        return new ResultSchema();
    }

    String betweenFragment(String attributeName) {
        if (isDateGiven()) {
            Date today = date();
            Date tomorrow = new Date(date().getTime() + dayLength());

            return new BetweenFragment(attributeName, today, tomorrow).toSQL();
        } else {
            return "";
        }
    }

    String ignoredCategoriesFragment(String attributeName) {
        return new NotInFragment(attributeName, ignoredCategories).toSQL();
    }

    String selectFragment() {
        return selectFragment;
    }

    String query() {
        return selectFragment() + whereClause();
    }

    String whereClause() {
        StringBuilder whereClause = new StringBuilder();

        if (isDateGiven() || areIgnoredCasesGiven()) {
            whereClause.append(" WHERE ");
            whereClause.append(
                    new AndFragment(
                            new NotInFragment("category_name", ignoredCategories),
                            new SimpleSQLFragment(betweenFragment("start_time")),
                            new SimpleSQLFragment(betweenFragment("end_time"))
                    ).toSQL()
            );
        }

        return whereClause.toString();
    }

    private Date date() {
        Calendar referenceCalendar = Calendar.getInstance();
        referenceCalendar.setTime(date);

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, referenceCalendar.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, referenceCalendar.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, referenceCalendar.get(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    private long dayLength() {
        return 24 * 60 * 60 * 1000;
    }

}
