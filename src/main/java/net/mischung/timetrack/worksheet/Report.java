package net.mischung.timetrack.worksheet;

import net.mischung.timetrack.DailyActivities;
import net.mischung.timetrack.DailyActivity;
import net.mischung.timetrack.worksheet.schema.ExportSchema;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Report {

    private static final List<String> extensions = Arrays.asList("xls", "xlsx");

    private final File workbookFile;

    public Report(File file) {
        this.workbookFile = file;
    }

    public void write(DailyActivities activities, ExportSchema schema) throws IOException {
        Workbook workbook = workbook();
        Sheet sheet = workbook.getSheetAt(sheetIndex(activities));

        for (DailyActivity activity : activities.getDailyActivities()) {
            Row row = sheet.createRow(nextRow(sheet));
            schema.writeActivity(row, activity);
        }

        workbook.write(new FileOutputStream(workbookFile));
    }

    int sheetIndex(DailyActivities activities) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(activities.getDate());
        return calendar.get(Calendar.MONTH);
    }

    String fileExtension() {
        String path = workbookFile.getPath();
        int dotIndex = path.lastIndexOf(".") + 1;
        return path.substring(dotIndex).toUpperCase();
    }

    int nextRow(Sheet sheet) {
        return sheet.getLastRowNum() + 1;
    }

    Workbook workbook() throws IOException {
        WorkbookFormat format = WorkbookFormat.valueOf(fileExtension());
        switch (format) {
            case XLS:
                return new HSSFWorkbook(new FileInputStream(workbookFile));
            case XLSX:
                return new XSSFWorkbook(new FileInputStream(workbookFile));
            default:
                throw new IllegalArgumentException("Format not supported");
        }
    }

}
