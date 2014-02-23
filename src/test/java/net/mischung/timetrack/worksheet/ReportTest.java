package net.mischung.timetrack.worksheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ReportTest {

    @Test
    public void testFileExtension() {
        Assert.assertEquals("XLS", new Report(new File("report.xls")).fileExtension());
        Assert.assertEquals("XLSX", new Report(new File("report.xlsx")).fileExtension());
        Assert.assertEquals("ODS", new Report(new File("report.ods")).fileExtension());
    }

    @Test
    public void testNextRow() throws IOException {
        Report report = new Report(new File("./src/test/resources/lastrownum.xls"));
        Workbook workbook = report.workbook();
        Assert.assertEquals(3, report.nextRow(workbook.getSheetAt(0)));
    }
}
