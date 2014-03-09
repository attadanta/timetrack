package net.mischung.timetrack.worksheet.schema;

import net.mischung.timetrack.DailyActivity;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ExportSchema {

    static enum Property {
        DATE,
        REL_DURATION,
        DURATION,
        NAME,
        CATEGORY,
        DESCRIPTION
    }

    private final Properties schema;

    public static ExportSchema fromFile(File file) throws IOException {
        Properties schemaProperties = new Properties();
        schemaProperties.load(new FileReader(file));
        return new ExportSchema(schemaProperties);
    }

    public ExportSchema(Properties properties) {
        this.schema = properties;
    }

    public void writeActivity(Row row, DailyActivity activity) {
        for (String output : outputs()) {
            Cell cell = row.createCell(cellIndex(output));
            Property property = resolveProperty(activity, propertyAt(output));
            ValueWriter<?> writer = valueWriter(activity, property);
            writer.setCellValue(cell);
        }
    }

    int cellIndex(String output) {
        return CellReference.convertColStringToIndex(output);
    }

    String propertyAt(String columnName) {
        String schemaProperty = schema.getProperty(columnName);

        if (schemaProperty != null) {
            return schemaProperty.toUpperCase();
        } else {
            return null;
        }
    }

    Set<String> outputs() {
        Set<String> result = new HashSet<String>();

        for (Object key : schema.keySet()) {
            if (key instanceof String) {
                result.add((String) key);
            }
        }

        return result;
    }

    boolean isGuardedProperty(String line) {
        return line.contains("|");
    }

    List<Property> splitGuardedProperty(String guardedProperty) {
        String[] tokens = guardedProperty.split("\\|+");
        List<Property> result = new ArrayList<Property>(tokens.length);

        for (String token : tokens) {
            result.add(Property.valueOf(token.trim().toUpperCase()));
        }

        return result;
    }

    ValueWriter<?> valueWriter(DailyActivity activity, Property property) {
        switch (property) {
            case DATE:
                return new DateValueWriter(activity.getDailyActivities().getDate());
            case REL_DURATION:
                return new DoubleValueWriter(relativeDuration(activity));
            case DURATION:
                return new DoubleValueWriter(activity.getDuration() / dayDuration());
            case NAME:
                return new StringValueWriter(activity.getName());
            case CATEGORY:
                return new StringValueWriter(activity.getCategory());
            case DESCRIPTION:
                return new StringValueWriter(activity.getDescription());

            default:
                throw new IllegalArgumentException("Property " + property + " not implemented");
        }
    }

    boolean hasProperty(DailyActivity activity, Property property) {
        switch (property) {
            case DATE:
                return true;
            case REL_DURATION:
                return true;
            case DURATION:
                return true;
            case NAME:
                return activity.getName() != null;
            case CATEGORY:
                return activity.getCategory() != null;
            case DESCRIPTION:
                return activity.getDescription() != null;
        }

        return false;
    }

    Property resolveProperty(DailyActivity activity, String property) {
        if (isGuardedProperty(property)) {
            return resolveGuardedProperty(activity, splitGuardedProperty(property));
        } else {
            return Property.valueOf(property.trim());
        }
    }

    Property resolveGuardedProperty(DailyActivity activity, List<Property> properties) {
        return resolveGuardedProperty(activity, properties.get(0), properties.get(1));
    }

    Property resolveGuardedProperty(DailyActivity activity, Property leftProperty, Property rightProperty) {
        if (hasProperty(activity, leftProperty)) {
            return leftProperty;
        } else {
            return rightProperty;
        }
    }

    private double dayDuration() {
        return Long.valueOf(24 * 60 * 60 * 1000L).doubleValue();
    }

    private double relativeDuration(DailyActivity activity) {
        long totalDuration = activity.getDailyActivities().totalDuration();
        return activity.getDuration() / Long.valueOf(totalDuration).doubleValue();
    }

}
