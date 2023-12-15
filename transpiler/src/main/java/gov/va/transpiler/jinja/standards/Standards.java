package gov.va.transpiler.jinja.standards;

public class Standards {
    public static final String INDENT = "\t";
    public static final String NEWLINE = "\n";
    public static final String FOLDER_SEPARATOR = "/";
    public static final String SINGLE_VALUE_COLUMN_NAME = "_val";
    public static final String EMPTY_TABLE = "SELECT * FROM (SELECT 1 " + SINGLE_VALUE_COLUMN_NAME + ") WHERE " + SINGLE_VALUE_COLUMN_NAME + " = 0";
    public static final String CONTEXT_ID_COLUMN_PARAM_PREFIX = "@Context";
    public static final String CONTEXT_ID_COLUMN_PARAM_SUFFIX = "IDColumnName";
}
