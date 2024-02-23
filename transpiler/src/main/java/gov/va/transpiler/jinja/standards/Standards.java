package gov.va.transpiler.jinja.standards;

public class Standards {
    public static final String INDENT = "\t";
    public static final String NEWLINE = "\n";
    public static final String FOLDER_SEPARATOR = "/";
    public static final String SINGLE_VALUE_COLUMN_NAME = "_val";
    private static final String MACRO_FILE_PREFIX = "_macros_";

    private static String targetLanguage;

    public static String getTargetLanguage() {
        return targetLanguage;
    }

    public static void setTargetLanguage(String targetLanguage) {
        Standards.targetLanguage = targetLanguage;
    }

    public static String macroFileName() {
        return MACRO_FILE_PREFIX + getTargetLanguage();
    }
}
