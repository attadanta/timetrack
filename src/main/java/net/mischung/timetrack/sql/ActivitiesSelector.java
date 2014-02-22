package net.mischung.timetrack.sql;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ActivitiesSelector {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        this.ignoredCategories = new ArrayList<String>(ignoredCategories.size());
        this.ignoredCategories.addAll(ignoredCategories);
    }

    ResultSchema getResultSchema() {
        return new ResultSchema();
    }

    String dateFragment(String attributeName) {
        return attributeName + " BETWEEN " + quoteLiteral(dateFormat.format(date())) +
                " AND " + quoteLiteral(dateFormat.format(new Date(date().getTime() + dayLength())));
    }

    String ignoredCategoriesFragment(String attributeName) {
        if (ignoredCategories.isEmpty()) {
            return "";
        }
        StringBuilder fragment = new StringBuilder();
        fragment.append(attributeName).append(" NOT IN(");
        for (int i = 0; i < ignoredCategories.size() - 1; i++) {
            fragment.append(quoteLiteral(ignoredCategories.get(i)));
            fragment.append(", ");
        }
        fragment.append(quoteLiteral(ignoredCategories.get(ignoredCategories.size() - 1)));
        fragment.append(")");
        return fragment.toString();
    }

    String selectFragment() {
        return selectFragment;
    }

    String query() {
        return selectFragment() + " WHERE " + ignoredCategoriesFragment("category_name")
                + " AND " + dateFragment("start_time") + " AND " + dateFragment("end_time");
    }

    private String quoteLiteral(String literal) {
        return "'" + literal + "'";
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
