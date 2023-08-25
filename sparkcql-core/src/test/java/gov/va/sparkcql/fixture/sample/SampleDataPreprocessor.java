package gov.va.sparkcql.fixture.sample;

import java.util.List;

import com.google.inject.Inject;
import gov.va.sparkcql.pipeline.preprocessor.AbstractReferenceDataPreprocessor;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.preprocessor.Preprocessor;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class SampleDataPreprocessor extends AbstractReferenceDataPreprocessor {

    @Inject
    public SampleDataPreprocessor(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    public void apply() {
        fromResourceJson(
                "sample/sample-data-patient.json",
                new DataType("http://va.gov/sparkcql/sample", "SamplePatient"));

        fromResourceJson(
                "sample/sample-data-entity.json",
                new DataType("http://va.gov/sparkcql/sample", "SampleEntity"));
    }
}