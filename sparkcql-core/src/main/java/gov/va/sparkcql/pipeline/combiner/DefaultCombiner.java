package gov.va.sparkcql.pipeline.combiner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.domain.RetrievalOperation;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;
import gov.va.sparkcql.pipeline.repository.clinical.ClinicalRepositorySchemaHelper;
import gov.va.sparkcql.types.DataType;
import scala.Tuple2;
import scala.Tuple21;
import scala.collection.JavaConverters;

public class DefaultCombiner implements Combiner {

    @Override
    public JavaPairRDD<String, List<Object>> combine(Map<RetrievalOperation, JavaRDD<Object>> retrieveMap, Plan plan, ModelAdapterResolver modelAdapterResolver) {
        
        // Group each dataset by the context and collect its interior clinical data as a
        // nested list so there's one outer row per member.
        var grouped = retrieveMap.entrySet().stream().map(e -> {

            // Convert JavaRDD to a JavaPairRDD where the first element 
            // represents the context ID of this instance.
            var dataType = new DataType(e.getKey().getRetrieve().getDataType());
            var ds = e.getValue();
            var modelAdapter = modelAdapterResolver.forType(dataType);
            
            var contextualizedRdd = ds.mapToPair(i -> {
                var contextId = modelAdapter.getContextId(i, plan.getContextDef());
                return Tuple2.apply(contextId, i);
            });

            // Group on the new context ID field and collect all instances as a list so
            // there's one row per context with all of it's data on the same row.
            return contextualizedRdd
                .groupByKey()
                .mapToPair(i -> {
                    var list = new ArrayList<>();
                    i._2.forEach(list::add);
                    return Tuple2.apply(i._1, list);
                });
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

            return left
                .join(right)
                .mapToPair(i -> {
                    var list = i._2._1;
                    list.addAll(i._2._2);
                    return Tuple2.apply(i._1, list);
                });

        }).get();

        var unmodified = combined.mapToPair(i -> Tuple2.apply(i._1, Collections.unmodifiableList(i._2)));

        return unmodified;
    }
}
