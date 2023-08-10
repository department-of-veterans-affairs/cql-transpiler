package gov.va.sparkcql.executor;

import java.util.stream.Collectors;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.hl7.elm.r1.Retrieve;

import static org.apache.spark.sql.functions.collect_list;

import gov.va.sparkcql.common.di.ServiceContext;
import gov.va.sparkcql.model.Plan;
import gov.va.sparkcql.repository.ClinicalDataRepositoryFactory;

public class SparkBulkRetriever implements BulkRetriever {

    @Override
    public Dataset<Row> retrieve(Plan plan, Object terminologyProvider) {

        var repositoryFactory = ServiceContext.createOne(ClinicalDataRepositoryFactory.class);

        // Acquire data for every retrieve operation as a series of datasets with
        // links back to retrieve definition which required it.
        var acquired = plan.getRetrievalOperations().stream()
            .collect(Collectors.toMap(r -> r, r -> {
                var repo = repositoryFactory.create(r.getRetrieve().getDataType());
                return repo.queryable();
            }));
        
        // Apply filters defined by the retrieve operation. These are calculated during the planning
        // generation of the ELM and subsequent planning phase.
        var filtered = acquired.entrySet().stream()
            .collect(Collectors.toMap(r -> r.getKey(), r -> {
                var retrieve = r.getKey().getRetrieve();
                var ds = r.getValue();
                var f = applyFilters(ds, retrieve);
                return ds;
            }));

        // Determine the established context if we're calculating patient, practitioner, or unfiltered.
        var contextColumn = resolveContextColumn(plan.getContextSpecifier());

        // Group each dataset by the context and collect its interior clinical data as a
        // nested list so there's one outer row per member. Add a hash of the retrieve operation
        // so we can still look it up later.
        var grouped = filtered.entrySet().stream().map(e -> {
            var hash = e.getKey().getHashKey();
            var ds = e.getValue();
            var g = ds
                .groupBy(ds.col(contextColumn))
                .agg(collect_list(ds.col("data")).alias(hash));
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
        var combined = grouped.stream().reduce((left, right) -> {
            var j = left
                .join(right, left.col(contextColumn).equalTo(right.col(contextColumn)), "inner")
                .drop(right.col(contextColumn));
            return j;
        }).get();

        return combined;
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

    protected Dataset<Row> applyFilters(Dataset<Row> ds, Retrieve retrieve) {
        ds = applyCodeInFilter(ds, retrieve);
        ds = applyDateFilter(ds, retrieve);
        return ds;
    }

    protected Dataset<Row> applyCodeInFilter(Dataset<Row> ds, Retrieve retrieve) {
        var filter = retrieve.getCodeFilter();
        if (filter.size() > 0)
            throw new UnsupportedOperationException();
        return ds;
    }

    protected Dataset<Row> applyDateFilter(Dataset<Row> ds, Retrieve retrieve) {
        var filter = retrieve.getDateFilter();
        if (filter.size() > 0)
            throw new UnsupportedOperationException();
        return ds;
    }
}