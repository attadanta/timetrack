package net.mischung.timetrack;

import java.io.File;
import java.util.Date;

public class TodaysActivities {

    public static void main(String[] args) throws Exception {
        File dbFile = new File(args[0]);

        Date date;
        if (args.length > 1) {
            date = ListActivities.dateFormat.parse(args[1]);
        } else {
            date = new Date();
        }

        for (Activity activity : new ListActivities(dbFile, date).activities()) {
            System.out.println(activity);
        }
    }

}
