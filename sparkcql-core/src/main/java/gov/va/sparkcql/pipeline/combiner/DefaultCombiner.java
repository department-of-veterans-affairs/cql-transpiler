package gov.va.sparkcql.pipeline.combiner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gov.va.sparkcql.domain.Retrieval;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;
import gov.va.sparkcql.types.DataType;
import org.hl7.elm.r1.Retrieve;
import scala.Tuple2;

public class DefaultCombiner implements Combiner {

    @Override
    public JavaPairRDD<String, Map<Retrieval, List<Object>>> combine(Map<Retrieval, JavaRDD<Object>> retrieveMap, Plan plan, ModelAdapterResolver modelAdapterResolver) {
        
        // Group each dataset by the context and collect its interior clinical data as a
        // nested list so there's one outer row per member.
        var grouped = retrieveMap.entrySet().stream().map(e -> {

            // Convert JavaRDD to a JavaPairRDD where the first element 
            // represents the context ID of this instance.
            var dataType = e.getKey().getDataType();
            var ds = e.getValue();
            var modelAdapter = modelAdapterResolver.forType(dataType);
            
            var contextualizedRdd = ds.mapToPair(i -> {
                var contextId = modelAdapter.getContextId(i, plan.getContextDef());
                return Tuple2.apply(contextId, i);
            });

            var rdd = contextualizedRdd.groupByKey();
            var x = rdd.collect();

            // Group on the new context ID field and collect all instances as a list so
            // there's one row per context with all of its data on the same row.
            return contextualizedRdd
                .groupByKey()
                .mapToPair(i -> {
                    // The map between the Retrieve and the RDD it produced must be maintained.
                    // TODO: Consider producing a composite Iterator instead of buffering to a List
                    // depending if it improves performance.
                    var m = Stream.of(i._2).collect(Collectors.toMap(k -> e.getKey(), v -> {
                        var list = new ArrayList<>();
                        i._2.forEach(list::add);
                        return Collections.unmodifiableList(list);
                    }));
                    return Tuple2.apply(i._1, m);
                });

        }).collect(Collectors.toList());

        // Perform an uber-join assimilating context specific data together
        // as a single row per context member. For example, a given patient will now
        // have a single row of data containing nested fields and lists of the clinical data
        // required by the runtime engine. A single dataset, single row per context member
        // also allows for simpler distributed processing since we can Map over the range.
        //
        // IMPORTANT: For best performance, all source data should be bucketed by the 
        // [Patient]CorrelationID to ensure these expensive joins are all co-located.
        return grouped.stream().reduce((left, right) -> {

            return left
                .join(right)
                .mapToPair(i -> {
                    var m = new HashMap<Retrieval, List<Object>>();
                    m.putAll(i._2._1);
                    m.putAll(i._2._2);
                    return Tuple2.apply(i._1, Collections.unmodifiableMap(m));
                });

        }).orElseThrow();
    }
}
