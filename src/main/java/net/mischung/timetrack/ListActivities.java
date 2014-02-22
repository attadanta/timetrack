package net.mischung.timetrack;

import net.mischung.timetrack.sql.Activities;
import net.mischung.timetrack.sql.ActivitiesSelector;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ListActivities {

    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final File databaseFile;
    private final Date date;

    ListActivities(File dbFile, Date date) {
        this.databaseFile = dbFile;
        this.date = date;
    }

    List<? extends Activity> activities() throws SQLException {
        ActivitiesSelector selector = new ActivitiesSelector(date, Arrays.asList("Pause"));
        List<SingleActivity> allActivities = new Activities(databaseFile, selector).all();
        return new DailyActivities(date, allActivities).getDailyActivities();
    }

}
