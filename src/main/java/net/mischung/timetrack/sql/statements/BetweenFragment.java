package net.mischung.timetrack.sql.statements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BetweenFragment extends SQLFragment {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String attributeName;
    private final Date leftDate;
    private final Date rightDate;

    public BetweenFragment(String attributeName, Date leftDate, Date rightDate) {
        this.attributeName = attributeName;
        this.leftDate = leftDate;
        this.rightDate = rightDate;
    }

    @Override
    public String toSQL() {
        return quoteAttribute(attributeName) + " " + "BETWEEN" + " " + new AndFragment(
                new SimpleSQLFragment(quoteLiteral(dateFormat.format(leftDate))),
                new SimpleSQLFragment(quoteLiteral(dateFormat.format(rightDate)))
        ).toSQL();
    }

}
