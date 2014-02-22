package net.mischung.timetrack;

import java.util.*;

public class DailyActivities {

    private final Date date;
    private final List<SingleActivity> allActivities;

    private List<SingleActivity> dailyActivities;

    public DailyActivities(Date date, List<SingleActivity> activities) {
        this.date = date;
        this.allActivities = activities;
    }

    public List<DailyActivity> getDailyActivities() {
        List<DailyActivity> dailyActivities = new LinkedList<DailyActivity>();
        LinkedList<SingleActivity> allActivities = new LinkedList<SingleActivity>(this.allActivities);

        while (!allActivities.isEmpty()) {
            Activity currentActivity = allActivities.poll();
            DailyActivity dailyActivity = new DailyActivity(currentActivity);

            for (Iterator<SingleActivity> iterator = allActivities.iterator(); iterator.hasNext();) {
                SingleActivity otherActivity = iterator.next();
                if (currentActivity.equals(otherActivity)) {
                    dailyActivity.addActivity(otherActivity);
                    iterator.remove();
                }
            }

            dailyActivities.add(dailyActivity);
        }

        return Collections.unmodifiableList(dailyActivities);
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

}
