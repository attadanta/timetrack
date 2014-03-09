package net.mischung.timetrack.cli.arguments;

import net.mischung.timetrack.cli.validation.Constraint;
import net.mischung.timetrack.cli.validation.PathExists;

import java.io.File;
import java.util.List;

public class FileArgument extends Argument<File> {

    public FileArgument(String name, int index) {
        super(name, index);
    }

    @Override
    public Constraint getConstraint() {
        return new PathExists(accessor);
    }

    @Override
    public File access(List<String> arguments) {
        return new File(accessor.get(arguments));
    }


}
