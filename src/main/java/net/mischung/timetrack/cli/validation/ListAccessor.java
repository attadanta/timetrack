package net.mischung.timetrack.cli.validation;

import java.util.List;

public class ListAccessor<T> {

    private final int index;

    public ListAccessor(int index) {
        this.index = index;
    }

    public boolean canAccess(List<T> list) {
        return list != null
                && index < list.size()
                && list.get(index) != null;
    }

    public T get(List<T> list) {
        if (!canAccess(list)) {
            throw new IllegalStateException("Cannot access the " + index + ". list element. Call canAccess() beforehand.");
        }
        return list.get(index);
    }

}
