package net.mischung.timetrack;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class DailyActivitiesTest {

    @Test
    public void testInit() {
        DailyActivities activities = new DailyActivities(new Date(), Collections.<SingleActivity>emptyList());
        Assert.assertNotNull(activities);
    }

    @Test
    public void getDailyActivities() {
        List<SingleActivity> activityList = new ArrayList<SingleActivity>(3);

        SingleActivity activityOne = new SingleActivity("Pick nose", date(1987, 11, 28, 0, 0), date(1987, 11, 28, 1, 0));
        SingleActivity activityTwo = new SingleActivity("Procrastinate", date(1987, 11, 28, 2, 0), date(1987, 11, 28, 2, 30));
        SingleActivity activityThree = new SingleActivity("Pick nose", date(1987, 11, 28, 2, 30), date(1987, 11, 28, 3, 0));

        activityList.add(activityOne);
        activityList.add(activityTwo);
        activityList.add(activityThree);

        DailyActivities dailyActivities = new DailyActivities(date(1987, 11, 28), activityList);

        List<DailyActivity> expectedList = new ArrayList<DailyActivity>(2);
        expectedList.add(new DailyActivity(activityOne));
        expectedList.add(new DailyActivity(activityTwo));

        Assert.assertArrayEquals(expectedList.toArray(), dailyActivities.getDailyActivities().toArray());
    }

    @Test
    public void getDailyActivitiesEmpty() {
        DailyActivities dailyActivities = new DailyActivities(new Date(), Collections.<SingleActivity>emptyList());
        Assert.assertArrayEquals(new Object[0], dailyActivities.getDailyActivities().toArray());
    }

    private Date date(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    private Date date(int year, int month, int day) {
        return date(year, month, day, 0, 0);
    }

}
