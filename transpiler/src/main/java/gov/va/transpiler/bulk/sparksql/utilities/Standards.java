package gov.va.transpiler.bulk.sparksql.utilities;

public class Standards {
    public static final String SINGLE_VALUE_COLUMN_NAME = "_val";
    public static final String EMPTY_TABLE = "SELECT * FROM (SELECT 1 " + SINGLE_VALUE_COLUMN_NAME + ") WHERE " + SINGLE_VALUE_COLUMN_NAME + " = 0";
    public static final String DEFAULT_CQL_DATE_TIME = "@2010-01-02T14:30:07.76";
    public static final String DEFAULT_SQL_DATE_TIME = "2010-01-02T14:30:07.76";
}
