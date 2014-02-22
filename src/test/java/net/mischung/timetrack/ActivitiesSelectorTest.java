package net.mischung.timetrack;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class ActivitiesSelectorTest {

    @Test
    public void testDateFragment() {
        ActivitiesSelector selector = new ActivitiesSelector(today(), Collections.<String>emptyList());
        Assert.assertEquals("date BETWEEN 1987-12-28 00:00:00 AND 1987-12-29 00:00:00", selector.dateFragment("date"));
    }

    @Test
    public void testIgnoredCategoriesFragment() {
        ActivitiesSelector categories = new ActivitiesSelector(today(), Arrays.asList("pause", "leisure"));
        Assert.assertEquals("category NOT IN('pause', 'leisure')", categories.ignoredCategoriesFragment("category"));
    }

    @Test
    public void testSingleIgnoredCategory() {
        ActivitiesSelector categories = new ActivitiesSelector(today(), Arrays.asList("leisure"));
        Assert.assertEquals("category NOT IN('leisure')", categories.ignoredCategoriesFragment("category"));
    }

    @Test
    public void testEmptyIgnoredCategoriesFragment() {
        ActivitiesSelector noCategories = new ActivitiesSelector(today(), Collections.<String>emptyList());
        Assert.assertEquals("", noCategories.ignoredCategoriesFragment("category"));
    }

    /**
     * Returns a random date.
     *
     * @return the date 1987-12-28.
     */
    private Date today() {
        Calendar todaysDate = Calendar.getInstance();
        todaysDate.set(Calendar.YEAR, 1987);
        todaysDate.set(Calendar.MONTH, 11);
        todaysDate.set(Calendar.DAY_OF_MONTH, 28);
        todaysDate.set(Calendar.HOUR, 11);
        todaysDate.set(Calendar.MINUTE, 11);
        todaysDate.set(Calendar.SECOND, 11);
        return todaysDate.getTime();
    }

}
