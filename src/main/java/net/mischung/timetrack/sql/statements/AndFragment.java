package net.mischung.timetrack.sql.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AndFragment extends SQLFragment {

    private final List<SQLFragment> conditionalFragments;

    public AndFragment(SQLFragment... conditionalFragments) {
        this.conditionalFragments = new ArrayList<SQLFragment>(conditionalFragments.length);
        this.conditionalFragments.addAll(Arrays.asList(conditionalFragments));
    }

    @Override
    public String toSQL() {
        StringBuilder fragment = new StringBuilder();
        List<String> nonEmptyFragments = nonEmptyFragments();

        if (nonEmptyFragments.isEmpty()) {
            return "";
        }

        for (int i = 0; i < nonEmptyFragments.size() - 1; i++) {
            fragment.append(nonEmptyFragments.get(i));
            fragment.append(" AND ");
        }

        fragment.append(nonEmptyFragments.get(nonEmptyFragments.size() - 1));

        return fragment.toString();
    }

    private List<String> nonEmptyFragments() {
        List<String> nonEmptyFragments = new LinkedList<String>();

        for (SQLFragment conditionalFragment : conditionalFragments) {
            String sql = conditionalFragment.toSQL();
            if (!sql.isEmpty()) {
                nonEmptyFragments.add(sql);
            }
        }

        return nonEmptyFragments;
    }

}
