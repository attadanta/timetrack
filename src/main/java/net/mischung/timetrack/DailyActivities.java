package net.mischung.timetrack;

import java.util.*;

public class DailyActivities {

    private final Date day;
    private final List<SingleActivity> allActivities;

    public DailyActivities(Date day, List<SingleActivity> activities) {
        this.day = day;
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

    public Date getDay() {
        return day;
    }

}
