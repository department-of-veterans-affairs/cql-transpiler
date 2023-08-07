package gov.va.sparkcql.common.configuration;

public enum ConfigKey {
    SPARKCQL_DEFAULT_TABLEBINDING,
    SPARKCQL_BULKRETRIEVER_CLASS,
    SPARKCQL_CQLSOURCEREPOSITORY_CLASS,
    SPARKCQL_CQLSOURCEREPOSITORY_PATH,
    SPARKCQL_SPARK_MASTER,
    SPARKCQL_SPARK_LOGLEVEL;

    public String toString() {
        return this.name().replace('_', '.').toLowerCase();
    }
}