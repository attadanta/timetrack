package net.mischung.timetrack;

import java.util.*;

public class DailyActivities {

    private final Date date;
    private final List<? extends Activity> allActivities;

    private List<DailyActivity> dailyActivities;

    public DailyActivities(Date date, List<? extends Activity> activities) {
        this.date = date;
        this.allActivities = activities;
    }

    public List<DailyActivity> getDailyActivities() {
        if (dailyActivities == null) {
            dailyActivities = computeDailyActivities();
            return dailyActivities;
        } else {
            return dailyActivities;
        }
    }

    public long totalDuration() {
        long duration = 0;

        for (Activity activity : getDailyActivities()) {
            duration = duration + activity.getDuration();
        }

        return duration;
    }

    public Date getDate() {
        return date;
    }

    private List<DailyActivity> computeDailyActivities() {
        List<DailyActivity> dailyActivities = new LinkedList<DailyActivity>();
        LinkedList<Activity> allActivities = new LinkedList<Activity>(this.allActivities);

        while (!allActivities.isEmpty()) {
            Activity currentActivity = allActivities.poll();
            DailyActivity dailyActivity = new DailyActivity(currentActivity);

            for (Iterator<Activity> iterator = allActivities.iterator(); iterator.hasNext(); ) {
                Activity otherActivity = iterator.next();
                if (currentActivity.equals(otherActivity)) {
                    dailyActivity.addActivity(otherActivity);
                    iterator.remove();
                }
            }

            dailyActivities.add(dailyActivity);
        }

        return Collections.unmodifiableList(dailyActivities);
    }

}
