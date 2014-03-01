package net.mischung.timetrack.worksheet.schema;

import net.mischung.timetrack.DailyActivity;
import net.mischung.timetrack.SingleActivity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExportSchemaTest {

    private ExportSchema exportSchema;

    @Before
    public void setUp() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File("./src/test/resources/output.properties")));
        this.exportSchema = new ExportSchema(properties);
    }

    @Test
    public void testCellAt() {
        Assert.assertEquals(0, exportSchema.cellIndex("A"));
        Assert.assertEquals(1, exportSchema.cellIndex("B"));
        Assert.assertEquals(2, exportSchema.cellIndex("C"));
    }

    @Test
    public void testIsGuardLine() {
        Assert.assertFalse(exportSchema.isGuardedProperty("DESCRIPTION"));
        Assert.assertTrue(exportSchema.isGuardedProperty("DESCRIPTION || NAME"));
    }

    @Test
    public void testSplitGuardLine() {
        Object[] expecteds = new ExportSchema.Property[]{ExportSchema.Property.DESCRIPTION, ExportSchema.Property.NAME};
        Object[] actuals = exportSchema.splitGuardedProperty("DESCRIPTION || NAME").toArray();

        Assert.assertArrayEquals("Split guarded property fail", expecteds, actuals);
    }

    @Test
    public void testResolveGuardedProperty() {
        DailyActivity activity = new DailyActivity(new SingleActivity("Nose picking", new Date(), new Date()));

        List<ExportSchema.Property> testCase = Arrays.asList(
                ExportSchema.Property.DESCRIPTION,
                ExportSchema.Property.NAME
        );
        Assert.assertEquals(
                ExportSchema.Property.NAME,
                exportSchema.resolveGuardedProperty(activity, testCase)
        );

        testCase = Arrays.asList(
                ExportSchema.Property.NAME,
                ExportSchema.Property.DESCRIPTION
        );
        Assert.assertEquals(
                ExportSchema.Property.NAME,
                exportSchema.resolveGuardedProperty(activity, testCase)
        );
    }

    @Test
    public void testOutputs() {
        Set<String> actuals = exportSchema.outputs();
        List<String> expecteds = Arrays.asList("A", "B", "C", "D", "E", "F", "G");

        Assert.assertTrue(actuals.containsAll(expecteds));
        Assert.assertEquals(expecteds.size(), actuals.size());
    }

    @Test
    public void testOutputAt() {
        Assert.assertEquals("REL_DURATION", exportSchema.propertyAt("A"));
        Assert.assertEquals("DESCRIPTION", exportSchema.propertyAt("E"));
        Assert.assertEquals("DESCRIPTION || CATEGORY", exportSchema.propertyAt("F"));
    }

}
