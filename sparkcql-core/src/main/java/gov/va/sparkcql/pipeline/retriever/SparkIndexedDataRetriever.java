package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.domain.Retrieval;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.col;

import java.util.ArrayList;
import java.util.List;

import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterComposite;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;

public class SparkIndexedDataRetriever implements Retriever {

    private final String ENCODED_DATA_COLUMN = "data";
    protected SparkSession spark;
    protected TableResolutionStrategy tableResolutionStrategy;

    public SparkIndexedDataRetriever(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        this.spark = sparkFactory.create();
        this.tableResolutionStrategy = tableResolutionStrategy;
    }

    @Override
    public JavaRDD<Object> retrieve(Retrieval retrieval, ModelAdapterComposite modelAdapterComposite) {
        
        // Acquire data for the retrieve operation with the assumption the data columns contains
        // the encoded data and additional promoted columns are provided to assist with filtering.
        var dataType = retrieval.getDataType();
        var boxedDs = spark.table(tableResolutionStrategy.resolveTableBinding(dataType));

        // Validate the source table conforms to the required "boxed" schematic.
        var boxedSchema = boxedDs.schema();
        validateSchema(boxedSchema);

        // Apply filters defined by the retrieve operation. These are calculated during the
        // generation of the ELM and subsequent optimization phase.
        applyFilters(boxedDs, retrieval);

        // Lookup the model adapter for the given data type and use it to decode the data.
        var modelAdapter = modelAdapterComposite.forType(dataType);
        var encoder = modelAdapter.getEncoder(dataType);
        var encodedDs = boxedDs.select(col(ENCODED_DATA_COLUMN + ".*"));
        
        return encodedDs.as(encoder).javaRDD();
    }

    private Dataset<Row> applyFilters(Dataset<Row> ds, Retrieval retrieval) {
        ds = applyCodeInFilter(ds, retrieval);
        ds = applyDateFilter(ds, retrieval);
        return ds;
    }

    private Dataset<Row> applyCodeInFilter(Dataset<Row> ds, Retrieval retrieval) {
//        var filter = retrieval.getCodeFilter();
//        if (!filter.isEmpty())
//            throw new UnsupportedOperationException();
        return ds;
    }

    private Dataset<Row> applyDateFilter(Dataset<Row> ds, Retrieval retrieval) {
//        var filter = retrieval.getDateFilter();
//        if (!filter.isEmpty())
//            throw new UnsupportedOperationException();
        return ds;
    }

    /**
     * A mismatch between the table and canonical schemas could be caused by:
     *   a. Table defines a superset of canonical
     *   b. Table defines a subset of canonical
     *   c. Both (a) and (b)
     * A mismatch may be entirely appropriate depending on the intention. Implementors are
     * allowed to define additional attributes in their tables for their own purposes. Additionally, 
     * not all FHIR attributes will be relevant for all implementations. Therefore, only
     * check the intersection of critical attributes between the schemas.
     * 
     * @param schema
     */

    public static void validateSchema(StructType schema) {
        // Ensure critical container level columns are present.
        var missingColumns = new ArrayList<String>();
        var requiredColumns = List.of("patientCorrelationId", "practictionerCorrelationId", "primaryCode", "primaryStartDate", "primaryEndDate", "dataType", "data");
        for (var column: requiredColumns) {
            if (schema.getFieldIndex(column).isEmpty())
                missingColumns.add(column);
        }
        if (!missingColumns.isEmpty()) {
            var missingColumnsMsg = String.join(", ", missingColumns);
            throw new RuntimeException("Table is missing the following container level attributes: " + missingColumnsMsg + ".");
        }
    }    
}