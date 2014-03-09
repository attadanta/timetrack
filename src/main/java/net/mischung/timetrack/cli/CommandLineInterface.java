package net.mischung.timetrack.cli;

import net.mischung.timetrack.cli.arguments.Arguments;
import net.mischung.timetrack.cli.arguments.Spec;

import java.util.Arrays;

abstract class CommandLineInterface {

    protected final Spec spec;

    protected CommandLineInterface(Spec spec) {
        this.spec = spec;
    }

    public Arguments process(String... args) {
        return new Arguments(spec, Arrays.asList(args));
    }

}
