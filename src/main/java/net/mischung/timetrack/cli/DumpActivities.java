package net.mischung.timetrack.cli;


import net.mischung.timetrack.ListActivities;
import net.mischung.timetrack.cli.validation.*;
import net.mischung.timetrack.worksheet.Report;
import net.mischung.timetrack.worksheet.schema.ExportSchema;

import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DumpActivities {

    public static void main(String[] args) throws Exception {
        Arguments arguments = new Arguments(args);

        ListActivities activitiesList = new ListActivities(
                arguments.getDatabaseFile(),
                arguments.getDate(),
                arguments.getIgnoredCategories());

        Properties schemaProperties = new Properties();
        schemaProperties.load(new FileReader(arguments.getSchemaFile()));
        ExportSchema schema = new ExportSchema(schemaProperties);

        Report report = new Report(arguments.getReportFile());
        report.write(activitiesList.dailyActivities(), schema);
    }

    public static final class Arguments {

        private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        private final List<String> arguments;

        private final GuardedAccessor<String> databaseFileAccessor;
        private final GuardedAccessor<String> schemaFileAccessor;
        private final GuardedAccessor<String> reportFileAccessor;
        private final GuardedAccessor<String> dateAccessor;

        private final boolean valid;

        public Arguments(String... cliArguments) {
            this.arguments = new ArrayList<String>(cliArguments.length);
            this.arguments.addAll(Arrays.asList(cliArguments));

            this.databaseFileAccessor = new ListAccessor<String>(arguments, 0);
            this.schemaFileAccessor = new ListAccessor<String>(arguments, 1);
            this.reportFileAccessor = new ListAccessor<String>(arguments, 2);
            this.dateAccessor = new ListAccessor<String>(arguments, 3);

            this.valid = doValidate();
        }

        public File getDatabaseFile() {
            return new File(databaseFileAccessor.get());
        }

        public File getSchemaFile() {
            return new File(schemaFileAccessor.get());
        }

        public File getReportFile() {
            return new File(reportFileAccessor.get());
        }

        public Date getDate() {
            try {
                return dateFormat.parse(dateAccessor.get());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        public List<String> getIgnoredCategories() {
            if (arguments.size() > 4) {
                return arguments.subList(4, arguments.size());
            } else {
                return Collections.<String>emptyList();
            }
        }

        private boolean doValidate() {
            return new Conjugation(
                    new LeastNumberOfArguments(4, arguments),
                    new PathExists(databaseFileAccessor),
                    new PathExists(schemaFileAccessor),
                    new PathExists(reportFileAccessor),
                    new MatchesDateFormat(dateAccessor, dateFormat)
            ).validate();
        }
    }

}
