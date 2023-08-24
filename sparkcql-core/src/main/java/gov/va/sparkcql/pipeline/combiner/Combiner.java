package gov.va.sparkcql.pipeline.combiner;

import java.util.List;
import java.util.Map;

import gov.va.sparkcql.domain.Retrieval;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.Stage;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;

public interface Combiner extends Stage {

    public JavaPairRDD<String, Map<Retrieval, List<Object>>> combine(Map<Retrieval, JavaRDD<Object>> retrieveMap, Plan plan, ModelAdapterResolver modelAdapterResolver);
}