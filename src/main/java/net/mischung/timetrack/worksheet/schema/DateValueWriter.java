package net.mischung.timetrack.worksheet.schema;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Date;

public class DateValueWriter extends ValueWriter<Date> {

    DateValueWriter(Date date) {
        super(date);
    }

    @Override
    public void setCellValue(Cell cell) {
        cell.setCellValue(value);
    }

}
