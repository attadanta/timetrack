package net.mischung.timetrack.worksheet.schema;

import org.apache.poi.ss.usermodel.Cell;

public class DoubleValueWriter extends ValueWriter<Double> {

    DoubleValueWriter(Double value) {
        super(value);
    }

    @Override
    public void setCellValue(Cell cell) {
        cell.setCellValue(value);
    }

}
