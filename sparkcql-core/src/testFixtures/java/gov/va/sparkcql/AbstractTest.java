package gov.va.sparkcql;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.pipeline.converger.ConvergerFactory;
import gov.va.sparkcql.pipeline.converger.DefaultConvergerFactory;
import gov.va.sparkcql.pipeline.optimizer.DefaultOptimizerFactory;
import gov.va.sparkcql.pipeline.optimizer.OptimizerFactory;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepositoryFactory;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepositoryFactory;
import gov.va.sparkcql.pipeline.retriever.RetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.SparkIndexedDataRetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategyFactory;
import gov.va.sparkcql.pipeline.runtime.LocalSparkFactory;
import gov.va.sparkcql.pipeline.runtime.SparkFactory;
import org.apache.spark.sql.SparkSession;

import static gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepositoryFactory.CQL_SOURCE_FILE_REPOSITORY_PATH;

public abstract class AbstractTest {

    // Default configuration settings. This can be overridden by the subclass.
    protected final Configuration configuration = new EnvironmentConfiguration()
            // Use a locally configured Spark for testing.
            .writeBinding(SparkFactory.class, LocalSparkFactory.class)
            // Most tests have CQL scripts under Resources.
            .writeBinding(CqlSourceRepositoryFactory.class, CqlSourceFileRepositoryFactory.class)
            .writeSetting(CQL_SOURCE_FILE_REPOSITORY_PATH, "./src/test/resources/")
            // Database support unavailable when running locally so drop the qualifier.
            .writeSetting(TemplateResolutionStrategyFactory.TEMPLATE_RESOLUTION_STRATEGY, "${model}_${domain}")
            // Expect most tests to leverage indexed spark tables.
            .writeBinding(RetrieverFactory.class, SparkIndexedDataRetrieverFactory.class)
            // Default Pipeline Implementations
            .writeBinding(OptimizerFactory.class, DefaultOptimizerFactory.class)
            .writeBinding(ConvergerFactory.class, DefaultConvergerFactory.class);

    protected final SparkFactory sparkFactory = new LocalSparkFactory();
    protected final SparkSession spark = sparkFactory.create(configuration);
}