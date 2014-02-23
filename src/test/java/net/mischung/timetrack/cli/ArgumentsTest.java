package net.mischung.timetrack.cli;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class ArgumentsTest {

    private String dbFile;

    @Before
    public void assertDatabaseFileExists() {
        dbFile = "./src/test/resources/hamster.db";
        Assert.assertTrue(new File(dbFile).exists());
    }

    @Test
    public void testInit() {
        Assert.assertNotNull(new Arguments(new String[]{"a", "b", "c"}));
        Assert.assertNotNull(new Arguments("a", "b", "c"));
    }

    @Test
    public void testValidate() {
        Assert.assertTrue(new Arguments(dbFile, "1987-12-28").validate());
        Assert.assertTrue(new Arguments(dbFile, "1987-12-28", "Pause").validate());
        Assert.assertTrue(new Arguments(dbFile, "1987-12-28", "Pause", "Recreation").validate());
    }

    @Test
    public void testInvalidate() {
        Assert.assertFalse(new Arguments().validate());
        Assert.assertFalse(new Arguments(dbFile).validate());
        Assert.assertFalse(new Arguments(dbFile, "xyz").validate());
    }

    @Test
    public void testGetDatabaseFile() {
        Arguments arguments = new Arguments(dbFile, "1987-12-28");
        Assert.assertEquals(new File(dbFile), arguments.getDatabaseFile());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetDatabaseFileOnInvalidInput() {
        Arguments arguments = new Arguments(dbFile);
        arguments.getDatabaseFile();
    }

    @Test
    public void testGetDate() {
        Assert.assertEquals(getDate(), new Arguments(dbFile, "1987-12-28").getDate());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetDateOnInvalidInput() {
        new Arguments().getDate();
    }

    @Test
    public void testGetIgnoredCategories() {
        Object[] expected = new String[]{"pause", "recreation"};
        Object[] actual = new Arguments(dbFile, "1987-12-28", "pause", "recreation").getIgnoredCategories().toArray();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetIgnoredCategoriesOnInvalidInput() {
        new Arguments().getIgnoredCategories();
    }

    private Date getDate() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, 1987);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 28);
        return cal.getTime();
    }

}
