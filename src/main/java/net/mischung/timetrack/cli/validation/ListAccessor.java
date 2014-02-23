package net.mischung.timetrack.cli.validation;

import java.util.List;

public class ListAccessor<T> implements GuardedAccessor<T> {

    private final int index;
    private final List<T> list;

    public ListAccessor(List<T> list, int index) {
        this.index = index;
        this.list = list;
    }

    @Override
    public boolean canAccess() {
        return list != null && index < list.size();
    }

    @Override
    public T get() {
        if (!canAccess()) {
            throw new IllegalStateException("Cannot access the " + index + ". list element. Call canAccess() beforehand.");
        }
        return list.get(index);
    }

}
