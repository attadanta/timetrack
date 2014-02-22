package net.mischung.timetrack;

import net.mischung.timetrack.sql.Activities;
import net.mischung.timetrack.sql.ActivitiesSelector;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

public class TodaysActivities {

    public static void main(String[] args) throws SQLException {

        File dbFile = new File(args[0]);
        Date todaysDate = new Date();

        ActivitiesSelector selector = new ActivitiesSelector(todaysDate, Arrays.asList("Pause"));
        Activities activities = new Activities(dbFile, selector);

        for (Activity activity : activities.all()) {
            System.out.println(activity);
        }

    }
}
