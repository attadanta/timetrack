package net.mischung.timetrack.cli.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Conjugation implements Constraint {

    private final List<Constraint> constraints;

    public Conjugation(Constraint... constraints) {
        this.constraints = new ArrayList<Constraint>(constraints.length);
        this.constraints.addAll(Arrays.asList(constraints));
    }

    @Override
    public boolean validate(List<String> arguments) {
        boolean valid = true;

        for (Iterator<Constraint> iterator = constraints.iterator(); valid && iterator.hasNext(); ) {
            Constraint nextConstraint = iterator.next();
            valid = valid && nextConstraint.validate(arguments);
        }

        return valid;
    }

}
