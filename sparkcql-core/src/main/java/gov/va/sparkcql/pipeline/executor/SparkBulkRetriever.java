package gov.va.sparkcql.pipeline.executor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.hl7.elm.r1.Retrieve;

import com.google.inject.Inject;

import static org.apache.spark.sql.functions.collect_list;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.repository.clinical.ClinicalRepositoryCollection;
import gov.va.sparkcql.pipeline.repository.clinical.ClinicalRepositorySchemaHelper;
import gov.va.sparkcql.pipeline.repository.clinical.SparkClinicalSchemaHelper;
import gov.va.sparkcql.types.DataType;

import scala.collection.JavaConverters;

public class SparkBulkRetriever implements BulkRetriever {

    private ClinicalRepositoryCollection repositoryResolver;

    @Inject
    public SparkBulkRetriever(ClinicalRepositoryCollection repositoryResolver) {
        this.repositoryResolver = repositoryResolver;
    }

    @Override
    public Dataset<Row> retrieve(Plan plan, Object terminologyProvider) {
        // Acquire data for every retrieve operation as a series of datasets with
        // links back to retrieve definition which required it.
        var acquired = plan.getRetrievalOperations().stream()
            .collect(Collectors.toMap(r -> r, r -> {
                var repo = repositoryResolver.forType(new DataType(r.getRetrieve().getDataType()));
                var ds = repo.acquire();
                SparkClinicalSchemaHelper.validateRequiredElements(ds.schema(), repo.getEntityDataType());
                return ds;
            }));
        
        // Apply filters defined by the retrieve operation. These are calculated during the planning
        // generation of the ELM and subsequent planning phase.
        var filtered = acquired.entrySet().stream()
            .collect(Collectors.toMap(r -> r.getKey(), r -> {
                var retrieve = r.getKey().getRetrieve();
                var ds = r.getValue();
                ds = applyFilters(ds, retrieve);
                return ds;
            }));

        // Determine the established context if we're calculating patient, practitioner, or unfiltered.
        var contextColumn = ClinicalRepositorySchemaHelper.resolveContextColumn(plan.getContextDef().getName());

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
        // also allows for simpler distributed processing since we can Map over the range.
        //
        // IMPORTANT: For best performance, all source data should be bucketed by the 
        // [Patient]CorrelationID to ensure these expensive joins are all colocated.
        var combined = grouped.stream().reduce((left, right) -> {

            var leftCols = Stream.of(left.columns())
                .map(c -> left.col(c))
                .toList();

            var rightCols = Stream.of(right.columns())
                .filter(c -> {
                    return !c.equals("patientCorrelationId")
                        && !List.of(left.columns()).contains(c);
                })
                .map(c -> right.col(c))
                .toList();

            var cols = Stream.concat(leftCols.stream(), rightCols.stream())
                .collect(Collectors.toList());
            var colSeq = JavaConverters.asScalaIteratorConverter(cols.iterator()).asScala().toSeq();

            var j = left
                .as("left")
                .join(right, left.col(contextColumn).equalTo(right.col(contextColumn)), "inner")
                .select(colSeq);

            return j;
        }).get();

        return combined;
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