package net.mischung.timetrack.cli.validation;

public interface GuardedAccessor<T> {

    public boolean canAccess();

    public T get();

}
