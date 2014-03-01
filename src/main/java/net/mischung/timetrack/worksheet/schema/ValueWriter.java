package net.mischung.timetrack.worksheet.schema;

import org.apache.poi.ss.usermodel.Cell;

abstract class ValueWriter<T> {

    protected final T value;

    ValueWriter(T value) {
        this.value = value;
    }

    public abstract void setCellValue(Cell cell);

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "value=" + value +
                '}';
    }

}
