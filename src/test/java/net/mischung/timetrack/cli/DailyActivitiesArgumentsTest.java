package net.mischung.timetrack.cli;

import net.mischung.timetrack.cli.arguments.Arguments;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyActivitiesArgumentsTest {

    private String dbFile;

    @Before
    public void assertDatabaseFileExists() {
        dbFile = "./src/test/resources/hamster.db";
        Assert.assertTrue(new File(dbFile).exists());
    }

    @Test
    public void testInit() {
        Assert.assertNotNull(new DailyActivitiesArguments(new String[]{"a", "b", "c"}));
        Assert.assertNotNull(new DailyActivitiesArguments("a", "b", "c"));
    }

    @Test
    public void testValidate() {
        Assert.assertTrue(new DailyActivitiesArguments(dbFile, "1987-12-28").validate());
        Assert.assertTrue(new DailyActivitiesArguments(dbFile, "1987-12-28", "Pause").validate());
        Assert.assertTrue(new DailyActivitiesArguments(dbFile, "1987-12-28", "Pause", "Recreation").validate());
    }

    @Test
    public void testInvalidate() {
        Assert.assertFalse(new DailyActivitiesArguments().validate());
        Assert.assertFalse(new DailyActivitiesArguments(dbFile).validate());
        Assert.assertFalse(new DailyActivitiesArguments(dbFile, "xyz").validate());
    }

    @Test
    public void testGetDatabaseFile() {
        DailyActivitiesArguments arguments = new DailyActivitiesArguments(dbFile, "1987-12-28");
        Assert.assertEquals(new File(dbFile), arguments.getDatabaseFile());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetDatabaseFileOnInvalidInput() {
        DailyActivitiesArguments arguments = new DailyActivitiesArguments(dbFile);
        arguments.getDatabaseFile();
    }

    @Test
    public void testGetDate() {
        Assert.assertEquals(getDate(), new DailyActivitiesArguments(dbFile, "1987-12-28").getDate());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetDateOnInvalidInput() {
        new DailyActivitiesArguments().getDate();
    }

    @Test
    public void testGetIgnoredCategories() {
        Object[] expected = new String[]{"pause", "recreation"};
        Object[] actual = new DailyActivitiesArguments(dbFile, "1987-12-28", "pause", "recreation").getIgnoredCategories().toArray();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetIgnoredCategoriesOnInvalidInput() {
        new DailyActivitiesArguments().getIgnoredCategories();
    }

    private Date getDate() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, 1987);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 28);
        return cal.getTime();
    }

    private static class DailyActivitiesArguments {

        private final Arguments.Spec spec;
        private final Arguments arguments;

        private DailyActivitiesArguments(String... args) {
            this.spec = Arguments.specify()
                    .file("database")
                    .date("date")
                    .restArguments("ignored_categories");
            this.arguments = new Arguments(spec, Arrays.asList(args));
        }

        boolean validate() {
            return arguments.isValid();
        }

        File getDatabaseFile() {
            return arguments.getFile("database");
        }

        Date getDate() {
            return arguments.getDate("date");
        }

        List<String> getIgnoredCategories() {
            return arguments.getRestArguments("ignored_categories");
        }

    }

}
