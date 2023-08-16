package gov.va.sparkcql.repository.clinical;

import org.apache.spark.sql.types.StructType;
import org.hl7.fhir.instance.model.api.IBaseResource;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

import au.csiro.pathling.encoders.EncoderConfig;
import au.csiro.pathling.encoders.FhirEncoders;
import au.csiro.pathling.encoders.SchemaConverter;
import au.csiro.pathling.encoders.datatypes.R4DataTypeMappings;
import ca.uhn.fhir.context.FhirContext;

public abstract class FhirSparkRepository<T> extends SparkClinicalRepository<T> {

    protected FhirContext fhirContext = FhirContext.forR4();
    protected R4DataTypeMappings dataTypeMappings = new R4DataTypeMappings();
    protected int maxNestingLevel = 5;

    protected FhirEncoders encoders = FhirEncoders.forR4()
        .withExtensionsEnabled(false)
        .getOrCreate();

    @Inject
    public FhirSparkRepository(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    public DataType getEntityDataType() {
        return new DataType()
            .withNamespaceUri("http://hl7.org/fhir")
            .withName(this.getEntityClass().getSimpleName());
    }

    @SuppressWarnings("unchecked")
    protected StructType getCanonicalSchema() {
        var schemaConverter = new SchemaConverter(fhirContext, dataTypeMappings, EncoderConfig.apply(maxNestingLevel, null, false));
        Class<IBaseResource> entityClass = (Class<IBaseResource>)getEntityClass();
        var schema = schemaConverter.resourceSchema(entityClass);
        return schema;
    }
}