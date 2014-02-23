package net.mischung.timetrack;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

public class TodaysActivities {

    public static void main(String[] args) throws Exception {
        File dbFile = new File(args[0]);

        Date date = args.length > 1 ? ListActivities.dateFormat.parse(args[1]) : new Date();

        for (Activity activity : new ListActivities(dbFile, date, Arrays.asList("Pause")).allActivities()) {
            System.out.println(activity);
        }
    }

}
