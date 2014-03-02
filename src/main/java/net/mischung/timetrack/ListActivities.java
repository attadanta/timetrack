package net.mischung.timetrack;

import net.mischung.timetrack.sql.Activities;
import net.mischung.timetrack.sql.ActivitiesSelector;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ListActivities {

    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final File databaseFile;
    private final Date date;
    private final Set<String> ignoredCategories;

    public ListActivities(File dbFile, Date date, Collection<String> ignoredCategories) {
        this.databaseFile = dbFile;
        this.date = date;

        this.ignoredCategories = new HashSet<String>(ignoredCategories.size());
        this.ignoredCategories.addAll(ignoredCategories);
    }

    List<? extends Activity> allActivities() throws SQLException {
        ActivitiesSelector selector = new ActivitiesSelector(date, ignoredCategories);
        return new Activities(databaseFile, selector).all();
    }

    public DailyActivities dailyActivities() throws SQLException {
        return new DailyActivities(date, allActivities());
    }

    public List<? extends Activity> collectDailyActivities() throws SQLException {
        return dailyActivities().getDailyActivities();
    }

}
