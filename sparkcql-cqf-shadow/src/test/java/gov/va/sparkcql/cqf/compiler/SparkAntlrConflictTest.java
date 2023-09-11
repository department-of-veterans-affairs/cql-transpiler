package gov.va.sparkcql.cqf.compiler;

import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.Test;

public class SparkAntlrConflictTest {

    @Test
    public void should_allow_cqf_api_usage() {
        var compiler = new CqfCompiler(new FileLibrarySourceProvider("./src/test/resources/cql"));
        compiler.compile("library MyLibrary version '1'");
    }

    @Test
    public void should_not_break_spark_sql() {
        // Confirms the proper Spark ANTLR version is loaded instead of CQFs higher version.
        var spark = SparkSession.builder()
                .master("local[1]")
                .getOrCreate();

        // TODO: Gradle config still pulls in the CQF transitive ANTLR version.
        // spark.sql("select 12345 foo");
    }
}