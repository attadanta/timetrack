package net.mischung.timetrack.cli.validation;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class ConjugationTest {

    @Test
    public void testValid() {
        Assert.assertTrue(new Conjugation(
                new LeastNumberOfArguments(0)).validate(Collections.<String>emptyList())
        );
    }

    @Test
    public void testInvalid() {
        Assert.assertFalse(new Conjugation(
                new LeastNumberOfArguments(2)).validate(Collections.<String>emptyList())
        );

    }

}
