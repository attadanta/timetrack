package net.mischung.timetrack.cli;

import net.mischung.timetrack.Activity;
import net.mischung.timetrack.ListActivities;
import net.mischung.timetrack.cli.arguments.Arguments;

import java.io.File;
import java.util.Date;
import java.util.List;

public class TodaysActivities {

    public static void main(String[] args) throws Exception {
        CommandLineInterface cli = new CommandLineInterface(
                Arguments.specify()
                        .file("database")
                        .date("date")
                        .restArguments("ignored_categories")
                        .makeSpec()
        );

        Arguments arguments = cli.process(args);
        File dbFile = arguments.getFile("database");
        Date date = arguments.getDate("date");
        List<String> ignoredCategories = arguments.getRestArguments("ignored_categories");

        ListActivities listActivities = new ListActivities(dbFile, date, ignoredCategories);
        for (Activity activity : listActivities.allActivities()) {
            System.out.println(activity);
        }
    }

}
