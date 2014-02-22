package net.mischung.timetrack;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

public class ActivityTest {

    private static final Random random = new Random();

    @Test
    public void testEqualityOnNonNullFields() {
        Assert.assertEquals(
                new SingleActivity("nosepick", timeNow(), timeLater()),
                new SingleActivity("nosepick", timeNow(), timeLater()));
    }

    @Test
    public void testNonEqualityOnNonNullFields() {
        Assert.assertNotEquals(
                new SingleActivity("nosepick", timeNow(), timeLater()),
                new SingleActivity("procrastinate", timeNow(), timeLater()));
    }

    @Test
    public void testEqualityOnNullFields() {
        SingleActivity left = new SingleActivity("nosepick", timeNow(), timeLater());
        left.setCategory("Procrastination");

        SingleActivity right = new SingleActivity("nosepick", timeNow(), timeLater());
        right.setCategory("Procrastination");

        Assert.assertEquals(left, right);

        left.setDescription("picking my nose");
        right.setDescription("picking my nose");

        Assert.assertEquals(left, right);

        left = new SingleActivity("nosepick", timeNow(), timeLater());
        right = new SingleActivity("nosepick", timeNow(), timeLater());

        left.setDescription("picking my nose");
        right.setDescription("picking my nose");

        Assert.assertEquals(left, right);

        left.setCategory("Procrastination");
        right.setCategory("Procrastination");

        Assert.assertEquals(left, right);
    }

    @Test
    public void testNonEqualityOnNullFields() {
        SingleActivity left = new SingleActivity("nosepick", timeNow(), timeLater());
        left.setCategory("Procrastination");

        SingleActivity right = new SingleActivity("nosepick", timeNow(), timeLater());
        right.setCategory("Leisure");

        Assert.assertNotEquals(left, right);

        left.setDescription("picking my nose");
        right.setDescription("picking my nose");

        Assert.assertNotEquals(left, right);

        left = new SingleActivity("nosepick", timeNow(), timeLater());
        right = new SingleActivity("nosepick", timeNow(), timeLater());

        left.setDescription("picking my nose");
        right.setDescription("picking my ear");

        Assert.assertNotEquals(left, right);

        left.setCategory("Procrastination");
        right.setCategory("Procrastination");

        Assert.assertNotEquals(left, right);
    }

    private Date timeNow() {
        return new Date();
    }

    private Date timeLater() {
        long offset = random.nextInt(6) * 60 * 1000l;
        return new Date(timeNow().getTime() + offset);
    }

}
