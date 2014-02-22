package net.mischung.timetrack;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class DailyActivityTest {

    @Test
    public void activityDuration() {
        SingleActivity activityOne = new SingleActivity("Pick nose",
                DateTime.date(1987, 11, 28, 0, 0),
                DateTime.date(1987, 11, 28, 1, 0));
        SingleActivity activityTwo = new SingleActivity("Pick nose",
                DateTime.date(1987, 11, 28, 1, 0),
                DateTime.date(1987, 11, 28, 2, 0));
        SingleActivity activityThree = new SingleActivity("Pick nose",
                DateTime.date(1987, 11, 28, 2, 30),
                DateTime.date(1987, 11, 28, 3, 0));

        DailyActivity dailyActivity = new DailyActivity(activityOne, Arrays.asList(activityTwo, activityThree));
        Assert.assertEquals(150 * 60 * 1000L, dailyActivity.getDuration());
    }

    @Test
    public void testDurationSingleActivity() {
        SingleActivity activity = new SingleActivity("Procrastinate",
                DateTime.date(1987, 11, 28, 0, 0),
                DateTime.date(1987, 11, 28, 1, 0));

        DailyActivity dailyActivity = new DailyActivity(activity);
        Assert.assertEquals(60 * 60 * 1000L, dailyActivity.getDuration());
    }
}
