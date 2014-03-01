package net.mischung.timetrack.cli;

import net.mischung.timetrack.cli.validation.*;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Arguments {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final List<String> arguments;
    private final GuardedAccessor<String> dateAccessor;
    private final GuardedAccessor<String> databaseFileAccessor;
    private final boolean valid;

    public Arguments(String... cliArguments) {
        this.arguments = new ArrayList<String>(cliArguments.length);
        this.arguments.addAll(Arrays.asList(cliArguments));

        this.databaseFileAccessor = new ListAccessor<String>(arguments, 0);
        this.dateAccessor = new ListAccessor<String>(arguments, 1);

        this.valid = doValidate();
    }

    public boolean validate() {
        return valid;
    }

    public File getDatabaseFile() {
        guard();
        return new File(databaseFileAccessor.get());
    }

    public Date getDate() {
        guard();
        try {
            return dateFormat.parse(dateAccessor.get());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getIgnoredCategories() {
        guard();
        return arguments.subList(2, arguments.size());
    }

    private boolean doValidate() {
        return new Conjugation(new LeastNumberOfArguments(2, arguments),
                new PathExists(databaseFileAccessor),
                new MatchesDateFormat(dateAccessor, dateFormat)).validate();
    }

    private void guard() {
        if (!valid) {
            throw new IllegalStateException("Arguments not valid!");
        }
    }

}
