package gov.va.sparkcql.pipeline.retriever;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.hl7.elm.r1.Retrieve;

import static org.apache.spark.sql.functions.col;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.domain.RetrievalOperation;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class SparkBoxEncodedDataRetriever implements Retriever {

    private final String ENCODED_DATA_COLUMN = "data";
    protected SparkSession spark;
    protected TableResolutionStrategy tableResolutionStrategy;

    @Inject
    public SparkBoxEncodedDataRetriever(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        this.spark = sparkFactory.create();
        this.tableResolutionStrategy = tableResolutionStrategy;
    }

    @Override
    public JavaRDD<Object> retrieve(RetrievalOperation retrievalOperation, ModelAdapterResolver modelAdapterResolver, Object terminologyProvider) {
        
        // Acquire data for the retrieve operation with the assumption the data columns contains
        // the encoded data and additional promoted columns are provided to assist with filtering.
        var dataType = new DataType(retrievalOperation.getRetrieve().getDataType());
        var boxedDs = spark.table(tableResolutionStrategy.resolveTableBinding(dataType));

        // Apply filters defined by the retrieve operation. These are calculated during the
        // generation of the ELM and subsequent optimization phase.
        applyFilters(boxedDs, retrievalOperation.getRetrieve());

        // Lookup the model adapter for the given data type and use it to decode the data.
        var modelAdapter = modelAdapterResolver.forType(dataType);
        var encoder = modelAdapter.getEncoder(dataType);
        var encodedDs = boxedDs.select(col(ENCODED_DATA_COLUMN));
        
        return encodedDs.as(encoder).javaRDD();
    }

    private Dataset<Row> applyFilters(Dataset<Row> ds, Retrieve retrieve) {
        ds = applyCodeInFilter(ds, retrieve);
        ds = applyDateFilter(ds, retrieve);
        return ds;
    }

    private Dataset<Row> applyCodeInFilter(Dataset<Row> ds, Retrieve retrieve) {
        var filter = retrieve.getCodeFilter();
        if (filter.size() > 0)
            throw new UnsupportedOperationException();
        return ds;
    }

    private Dataset<Row> applyDateFilter(Dataset<Row> ds, Retrieve retrieve) {
        var filter = retrieve.getDateFilter();
        if (filter.size() > 0)
            throw new UnsupportedOperationException();
        return ds;
    }    
}