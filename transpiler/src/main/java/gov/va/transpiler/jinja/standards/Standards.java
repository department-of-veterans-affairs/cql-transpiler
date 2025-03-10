package gov.va.transpiler.jinja.standards;

/**
 * Used to collect widely-used string fragments and constants, so they can later be provided via system property or program argument.
 */
public class Standards {
    public static final String INDENT = "\t";
    public static final String NEWLINE = "\n";
    public static final String FOLDER_SEPARATOR = "/";
    public static final String SINGLE_VALUE_COLUMN_NAME = "_val";
    public static final String UNSUPPORTED_OPERATOR = "Unsupported";
    public static final String DISABLED_OPERATOR = "Disabled";
    public static final String UNFILTERED_CONTEXT = "Unfiltered";
    public static final String JINJA_FILE_POSTFIX = ".j2";
    public static final String ENVIRONMENT_NAME = "environment";
    public static final String STATE_NAME = "state";
    public static final String OPERATOR_HANDLER_NAME = "OperatorHandler";
    public static final String PRINT_FUNCTION_NAME = "print";
    public static final String GENERATED_INTERMEDIATE_AST_FOLDER = "generated_intermediate" + FOLDER_SEPARATOR;
    public static final String GENERATED_INTERMEDIATE_MODEL_FOLDER = "generated_models" + FOLDER_SEPARATOR;
    public static final String CUSTOM_OVERRIDES_FOLDER = "custom_overrides" + FOLDER_SEPARATOR;
    private static final String OPERATOR_MACRO_FILE_PREFIX = "_operators";
    private static final String CUSTOM_MACRO_FILE_PREFIX = "_custom";
    private static String targetLanguage;

    /**
     * @return Name of the target language the transpiler should ultimately prepare to convert CQL into.
     */
    public static String getTargetLanguage() {
        return targetLanguage;
    }

    /**
     * @param targetLanguage Name of the target language the transpiler should ultimately prepare to convert CQL into.
     */
    public static void setTargetLanguage(String targetLanguage) {
        Standards.targetLanguage = targetLanguage;
    }

    /**
     * @return Relative location of file containing macros for operators for the intermediate AST.
     */
    public static String macroFileName() {
        return getTargetLanguage() + "/" + OPERATOR_MACRO_FILE_PREFIX +  "_" + getTargetLanguage() + JINJA_FILE_POSTFIX;
    }

    /**
     * 
     * @return Alias to use for the operator macro file.
     */
    public static String macroFileReferenceName() {
        return OPERATOR_MACRO_FILE_PREFIX;
    }

    public static String getCustomFunctionFileName() {
        return CUSTOM_MACRO_FILE_PREFIX + getTargetLanguage() + JINJA_FILE_POSTFIX;
    }
}
