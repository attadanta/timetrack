package net.mischung.timetrack.worksheet.schema;

import org.apache.poi.ss.usermodel.Cell;

public class StringValueWriter extends ValueWriter<String> {

    StringValueWriter(String value) {
        super(value);
    }

    @Override
    public void setCellValue(Cell cell) {
        cell.setCellValue(value);
    }

}

