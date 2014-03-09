package net.mischung.timetrack.cli;

import net.mischung.timetrack.ListActivities;
import net.mischung.timetrack.cli.arguments.Arguments;
import net.mischung.timetrack.worksheet.Report;
import net.mischung.timetrack.worksheet.schema.ExportSchema;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Properties;

public class DumpDailyActivities {

    public static void main(String[] args) throws Exception {
        Arguments arguments = new CLI().process(args);

        ListActivities activitiesList = new ListActivities(
                arguments.getFile("database"),
                arguments.getDate("date"),
                arguments.getRestArguments("ignoredCategories"));

        Properties schemaProperties = new Properties();
        schemaProperties.load(new FileReader(arguments.getFile("schema")));
        ExportSchema schema = new ExportSchema(schemaProperties);

        Report report = new Report(arguments.getFile("report"));
        report.write(activitiesList.dailyActivities(), schema);
    }

    public static final class CLI extends CommandLineInterface {

        public CLI() {
            super(Arguments.specify()
                    .file("database")
                    .file("schema")
                    .file("report")
                    .date("date")
                    .restArguments("ignoredCategories")
                    .makeSpec());
        }

        public Arguments process(String... args) {
            return new Arguments(spec, Arrays.asList(args));
        }

    }

}
