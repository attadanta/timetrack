package net.mischung.timetrack.cli.arguments;

import java.util.List;
import java.util.Map;

public class Spec {

    private final List<Argument> arguments;
    private final Map<String, Integer> argMap;

    Spec(SpecFactory factory) {
        this.arguments = factory.getArguments();
        this.argMap = factory.getArgumentMap();
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public Argument getArgumentAt(int index) {
        return arguments.get(index);
    }

    public int argumentsSize() {
        return arguments.size();
    }

    public Argument getArgument(String name) {
        return arguments.get(argMap.get(name));
    }

}