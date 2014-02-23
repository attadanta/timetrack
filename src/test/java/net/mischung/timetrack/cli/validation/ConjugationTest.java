package net.mischung.timetrack.cli.validation;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class ConjugationTest {

    @Test
    public void testValid() {
        Assert.assertTrue(new Conjugation(
                new LeastNumberOfArguments(0, Collections.<String>emptyList())).validate()
        );

        Assert.assertTrue(new Conjugation(
                new LeastNumberOfArguments(0, Collections.<String>emptyList()),
                new LeastNumberOfArguments(1, Arrays.asList("a"))).validate()
        );
    }

    @Test
    public void testInvalid() {
        Assert.assertFalse(new Conjugation(
                new LeastNumberOfArguments(2, Collections.<String>emptyList())).validate()
        );

        Assert.assertFalse(new Conjugation(
                new LeastNumberOfArguments(0, Collections.<String>emptyList()),
                new LeastNumberOfArguments(2, Arrays.asList("a"))).validate()
        );
    }

}
