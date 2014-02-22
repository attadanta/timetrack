package net.mischung.timetrack.sql;

import net.mischung.timetrack.Activity;
import net.mischung.timetrack.SingleActivity;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public abstract class Activities {

    private final File databaseFile;
    private final ActivitiesSelector selector;

    public Activities(File databaseFile, ActivitiesSelector selector) {
        this.databaseFile = databaseFile;
        this.selector = selector;
    }

    abstract Connection initializeConnection();

    abstract List<Activities> all();

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
