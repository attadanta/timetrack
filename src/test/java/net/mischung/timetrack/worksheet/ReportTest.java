package net.mischung.timetrack.worksheet;

import net.mischung.timetrack.Activity;
import net.mischung.timetrack.DailyActivities;
import net.mischung.timetrack.DateTime;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class ReportTest {

    @Test
    public void testFileExtension() {
        Assert.assertEquals("XLS", new Report(new File("report.xls")).fileExtension());
        Assert.assertEquals("XLSX", new Report(new File("report.xlsx")).fileExtension());
        Assert.assertEquals("ODS", new Report(new File("report.ods")).fileExtension());
    }

    @Test
    public void testSheetIndex() {
        DailyActivities activities = new DailyActivities(DateTime.date(1997, 0, 29), Collections.<Activity>emptyList());
        Assert.assertEquals(0, new Report(new File("1997.xls")).sheetIndex(activities));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnsupportedFormat() throws IOException {
        Workbook workbook = new Report(new File("report.doc")).workbook();
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);
    }

    @Test
    public void testNextRow() throws IOException {
        Report report = new Report(new File("./src/test/resources/lastrownum.xls"));
        Workbook workbook = report.workbook();
        Assert.assertEquals(3, report.nextRow(workbook.getSheetAt(0)));
    }

}
