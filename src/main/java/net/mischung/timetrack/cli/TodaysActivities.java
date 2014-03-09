package net.mischung.timetrack.cli;

import net.mischung.timetrack.Activity;
import net.mischung.timetrack.ListActivities;
import net.mischung.timetrack.cli.arguments.Arguments;

import java.io.File;
import java.util.Date;
import java.util.List;

public class TodaysActivities {

    public static void main(String[] args) throws Exception {

        Arguments arguments = new CLI().process(args);
        File dbFile = arguments.getFile("database");
        Date date = arguments.getDate("date");
        List<String> ignoredCategories = arguments.getRestArguments("ignored_categories");

        for (Activity activity : new ListActivities(dbFile, date, ignoredCategories).allActivities()) {
            System.out.println(activity);
        }
    }

    private static class CLI extends CommandLineInterface {


        private CLI() {
            super(Arguments.specify()
                    .file("database")
                    .date("date")
                    .restArguments("ignored_categories")
                    .makeSpec()
            );
        }

    }

}
