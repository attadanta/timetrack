package net.mischung.timetrack.sql.statements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotInFragment extends SQLFragment {

    private final String attributeName;
    private final List<String> items;

    public NotInFragment(String attributeName, Collection<String> items) {
        this.attributeName = attributeName;

        this.items = new ArrayList<String>(items.size());
        this.items.addAll(items);
    }

    @Override
    public String toSQL() {
        if (items.isEmpty()) {
            return "";
        }

        StringBuilder fragment = new StringBuilder();
        fragment.append(quoteAttribute(attributeName)).append(" NOT IN(");
        for (int i = 0; i < items.size() - 1; i++) {
            fragment.append(quoteLiteral(items.get(i)));
            fragment.append(", ");
        }
        fragment.append(quoteLiteral(items.get(items.size() - 1)));
        fragment.append(")");

        return fragment.toString();
    }
}
