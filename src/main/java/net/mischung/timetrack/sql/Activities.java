package net.mischung.timetrack.sql;

import net.mischung.timetrack.SingleActivity;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Activities {

    private static final DateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final File databaseFile;
    private final ActivitiesSelector selector;

    public Activities(File databaseFile, ActivitiesSelector selector) {
        this.databaseFile = databaseFile;
        this.selector = selector;
    }

    public List<SingleActivity> all() throws SQLException {
        Connection connection = initializeConnection();
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(selector.query());

        List<SingleActivity> activities = new LinkedList<SingleActivity>();
        while (results.next()) {
            try {
                activities.add(toActivity(results));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        connection.close();

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

    private SingleActivity toActivity(ResultSet results) throws SQLException, ParseException {
        ResultSchema schema = selector.getResultSchema();
        String activityName = results.getString(schema.getActivityNameColumn());
        Date startTime = timestampFormat.parse(results.getString(schema.getActivityStartTimeColumn()));
        Date endTime = timestampFormat.parse(results.getString(schema.getActivityEndTimeColumn()));
        String description = results.getString(schema.getActivityDescriptionColumn());
        String category = results.getString(schema.getActivityCategoryColumn());

        SingleActivity activity = new SingleActivity(activityName, startTime, endTime);
        activity.setDescription(description);
        activity.setCategory(category);

        return activity;
    }

}
