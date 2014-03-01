package net.mischung.timetrack.sql.statements;

public class SimpleSQLFragment extends SQLFragment {

    private final String fragment;

    public SimpleSQLFragment(String fragment) {
        this.fragment = fragment;
    }

    @Override
    public String toSQL() {
        return fragment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleSQLFragment that = (SimpleSQLFragment) o;

        if (!fragment.equals(that.fragment)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return fragment.hashCode();
    }

    @Override
    public String toString() {
        return "SimpleSQLFragment{" +
                "fragment='" + fragment + '\'' +
                '}';
    }

}
