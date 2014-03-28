package net.mischung.timetrack.cli;

import net.mischung.timetrack.ListActivities;
import net.mischung.timetrack.cli.arguments.Arguments;
import net.mischung.timetrack.worksheet.Report;
import net.mischung.timetrack.worksheet.schema.ExportSchema;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DumpYear {

    private final Date beginDate;
    private final Date endDate;

    public DumpYear(Date currentTime) {
        this.beginDate = beginDate(currentTime);
        this.endDate = endDate(currentTime);
    }

    Date endDate(Date currentTime) {
        Calendar refCalendar = getReferenceCalendar(currentTime);

        Calendar calendar = getDate(
                refCalendar.get(Calendar.YEAR),
                refCalendar.get(Calendar.MONTH),
                refCalendar.get(Calendar.DAY_OF_MONTH)
        );

        return calendar.getTime();
    }

    Date beginDate(Date currentTime) {
        Calendar refCalendar = getReferenceCalendar(currentTime);
        Calendar calendar = getDate(refCalendar.get(Calendar.YEAR),
                Calendar.JANUARY,
                1);
        return calendar.getTime();
    }

    List<Date> dateRange() {
        List<Date> dates = new LinkedList<Date>();

        Date currentDate = this.beginDate;
        do {
            dates.add(currentDate);
            currentDate = nextDay(currentDate);
        } while (!currentDate.equals(endDate));

        return dates;
    }

    Date nextDay(Date currentDay) {
        Calendar refCalendar = getReferenceCalendar(currentDay);

        Calendar calendar = getDate(
                refCalendar.get(Calendar.YEAR),
                refCalendar.get(Calendar.MONTH),
                refCalendar.get(Calendar.DAY_OF_MONTH) + 1
        );

        return calendar.getTime();
    }

    private Calendar getReferenceCalendar(Date currentTime) {
        Calendar refCalendar = Calendar.getInstance();
        refCalendar.setTime(currentTime);
        return refCalendar;
    }

    private Calendar getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);
        return calendar;
    }

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws IOException, SQLException {
        CommandLineInterface cli = new CommandLineInterface(
                Arguments.specify()
                        .file("database")
                        .file("schema")
                        .file("report")
                        .restArguments("ignored_categories")
                        .makeSpec()
        );

        Arguments arguments = cli.process(args);

        ExportSchema schema = ExportSchema.fromFile(arguments.getFile("schema"));

        for (Date date : new DumpYear(new Date()).dateRange()) {
            System.out.println("Dumping " + dateFormat.format(date));

            ListActivities listActivities = new ListActivities(
                    arguments.getFile("database"),
                    date,
                    arguments.getRestArguments("ignored_categories")
            );
            Report report = new Report(arguments.getFile("report"));
            report.write(listActivities.dailyActivities(), schema);
        }

    }

}
