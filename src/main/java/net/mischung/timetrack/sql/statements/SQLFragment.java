package net.mischung.timetrack.sql.statements;

public abstract class SQLFragment {

    protected String quoteLiteral(String literal) {
        return "'" + literal + "'";
    }

    protected String quoteAttribute(String attribute) {
        return "`" + attribute + "`";
    }

    public abstract String toSQL();

}
