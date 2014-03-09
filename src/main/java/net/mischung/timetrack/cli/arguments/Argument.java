package net.mischung.timetrack.cli.arguments;

import net.mischung.timetrack.cli.validation.Constraint;
import net.mischung.timetrack.cli.validation.ListAccessor;

import java.util.List;

abstract class Argument<T> {

    protected final String name;
    protected final int index;
    protected final ListAccessor<String> accessor;

    protected Argument(String name, int index) {
        this.name = name;
        this.index = index;
        this.accessor = new ListAccessor<String>(index);
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public abstract Constraint getConstraint();

    public abstract T access(List<String> arguments);

}
