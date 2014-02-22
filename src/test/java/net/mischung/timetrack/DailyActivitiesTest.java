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

        SingleActivity activityOne = new SingleActivity("Pick nose", DateTime.date(1987, 11, 28, 0, 0), DateTime.date(1987, 11, 28, 1, 0));
        SingleActivity activityTwo = new SingleActivity("Procrastinate", DateTime.date(1987, 11, 28, 2, 0), DateTime.date(1987, 11, 28, 2, 30));
        SingleActivity activityThree = new SingleActivity("Pick nose", DateTime.date(1987, 11, 28, 2, 30), DateTime.date(1987, 11, 28, 3, 0));

        activityList.add(activityOne);
        activityList.add(activityTwo);
        activityList.add(activityThree);

        DailyActivities dailyActivities = new DailyActivities(DateTime.date(1987, 11, 28), activityList);

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


}
