package gov.va.sparkcql.executor;

import java.util.stream.Collectors;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import static org.apache.spark.sql.functions.collect_list;
import static org.apache.spark.sql.functions.lit;

import gov.va.sparkcql.common.di.ServiceContext;
import gov.va.sparkcql.model.Plan;
import gov.va.sparkcql.repository.ClinicalDataRepositoryFactory;

public class SparkBulkRetriever implements BulkRetriever {

    @Override
    public Dataset<Row> retrieve(Plan plan, Object terminologyProvider) {

        var repositoryFactory = ServiceContext.createOne(ClinicalDataRepositoryFactory.class);

        // Acquire data for every retrieve operation as a series of datasets with
        // links back to retrieve definition which required it.
        var acquired = plan.getRetrievalOperations()
            .stream()
            .collect(Collectors.toMap(r -> r, r -> {
                var repo = repositoryFactory.create(r.getRetrieve().getDataType());
                return repo.queryable();
            }));

        // Determine the established context if we're calculating patient, practitioner, or other.
        var contextColumn = resolveContextColumn(plan.getContextSpecifier());

        // Group each dataset by the context and collect its interior clinical data as a
        // nested list so there's one outer row per member. Add a hash of the retrieve operation
        // so we can still look it up later.
        var grouped = acquired.entrySet().stream().map(e -> {
            var hash = e.getKey().generateHash();
            var ds = e.getValue();
            var g = ds
                .groupBy(ds.col(contextColumn))
                .agg(collect_list(ds.col("data")))
                .withColumn("hash", lit(hash));
            g.show();
            return g;
        }).toList();

        // For the grande finale, perform an uber-join assimilating context specific data
        // together as a single row per context member. For example, a given patient will now
        // have a single row of data containing nested fields and lists of the clinical data
        // required by the runtime engine. A single dataset, single row per context member
        // also allows for simpler distributed processing.
        //
        // IMPORTANT: For best performance, all source data should be bucketed by the 
        // [Patient]CorrelationID to ensure these expensive joins are all colocated.
        var joined = grouped.stream().reduce((left, right) -> {
            var combined = left
                .join(right, left.col(contextColumn).equalTo(right.col(contextColumn)), "inner");
            return combined;
        }).get();

        joined.show();
        // acquiredRetrieves.forEach((r, ds) -> {
        //     ds.show();
        // });

        return null;
    }

    protected String resolveContextColumn(String contextSpecifier) {
        if (contextSpecifier.equals("Patient")) {
            return "patientCorrelationId";
        } else if (contextSpecifier.equals("Practitioner")) {
            return "practitionerCorrelationId";
        } else {
            throw new RuntimeException("Unsupported CQL context 'unfiltered'.");
        }
    }
}