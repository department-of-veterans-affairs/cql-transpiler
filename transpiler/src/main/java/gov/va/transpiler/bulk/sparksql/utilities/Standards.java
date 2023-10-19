package gov.va.transpiler.bulk.sparksql.utilities;

public class Standards {
    public static final String SINGLE_VALUE_COLUMN_NAME = "_val";
    public static final String EMPTY_TABLE = "SELECT * FROM (SELECT 1 " + SINGLE_VALUE_COLUMN_NAME + ") WHERE " + SINGLE_VALUE_COLUMN_NAME + " = 0";
}
