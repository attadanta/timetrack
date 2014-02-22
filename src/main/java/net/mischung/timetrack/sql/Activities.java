package net.mischung.timetrack.sql;

import net.mischung.timetrack.Activity;
import net.mischung.timetrack.SingleActivity;

import java.io.File;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Activities {

    private final File databaseFile;
    private final ActivitiesSelector selector;

    public Activities(File databaseFile, ActivitiesSelector selector) {
        this.databaseFile = databaseFile;
        this.selector = selector;
    }

    public List<Activity> all() throws SQLException {
        Connection connection = initializeConnection();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(selector.query());

        List<Activity> activities = new LinkedList<Activity>();
        while (results.next()) {
            activities.add(toActivity(results));
        }

        return activities;
    }

    private Connection initializeConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getPath());
        } catch (Exception e) {
            // TODO Differential exception handling.
            throw new RuntimeException(e);
        }
    }

    private Activity toActivity(ResultSet results) throws SQLException {
        ResultSchema schema = selector.getResultSchema();
        String activityName = results.getString(schema.getActivityNameColumn());
        Date startTime = results.getTimestamp(schema.getActivityStartTimeColumn());
        Date endTime = results.getTimestamp(schema.getActivityEndTimeColumn());
        String description = results.getString(schema.getActivityDescriptionColumn());
        String category = results.getString(schema.getActivityCategoryColumn());

        Activity activity = new SingleActivity(activityName, startTime, endTime);
        activity.setDescription(description);
        activity.setCategory(category);

        return activity;
    }

}
