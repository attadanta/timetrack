package net.mischung.timetrack.cli.arguments;

import java.util.*;

public class SpecFactory {

    private final List<Argument> arguments;
    private final Map<String, Integer> argMap;

    public SpecFactory() {
        this.arguments = new LinkedList<Argument>();
        this.argMap = new HashMap<String, Integer>();
    }

    public SpecFactory file(String name) {
        addArgument(new FileArgument(name, arguments.size()));
        return this;
    }

    public SpecFactory date(String name) {
        addArgument(new DateArgument(name, arguments.size()));
        return this;
    }

    public SpecFactory restArguments(String name) {
        addArgument(new RestArguments(name, arguments.size()));
        return this;
    }

    public Spec makeSpec() {
        return new Spec(this);
    }

    List<Argument> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    public Map<String, Integer> getArgumentMap() {
        return Collections.unmodifiableMap(argMap);
    }

    private void addArgument(Argument argument) {
        arguments.add(argument);
        argMap.put(argument.getName(), argument.getIndex());
    }

}
