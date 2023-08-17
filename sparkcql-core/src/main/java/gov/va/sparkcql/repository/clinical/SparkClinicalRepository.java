package gov.va.sparkcql.repository.clinical;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;

public abstract class SparkClinicalRepository<T> implements ClinicalRepository<T> {

    protected SparkSession spark;
    protected TableResolutionStrategy tableResolutionStrategy;

    @Inject
    public SparkClinicalRepository(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        this.spark = sparkFactory.create();
        this.tableResolutionStrategy = tableResolutionStrategy;
    }

    protected abstract StructType getCanonicalSchema();

    @Override
    public Dataset<Row> acquire() {
        var ds = spark.table(tableResolutionStrategy.resolveTableBinding(getEntityDataType()));
        SparkClinicalSchemaHelper.validateSchema(ds.schema(), getCanonicalSchema(), getEntityDataType());
        return ds;
    }

    protected String resolveTable() {
        return getEntityClass().getSimpleName();
    }
}