package gov.va.sparkcql.pipeline.combiner;

import java.util.List;
import java.util.Map;

import gov.va.sparkcql.domain.Retrieval;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.Component;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;
import org.hl7.elm.r1.Retrieve;

public interface Combiner extends Component {

    public JavaPairRDD<String, Map<Retrieval, List<Object>>> combine(Map<Retrieval, JavaRDD<Object>> retrieveMap, Plan plan, ModelAdapterResolver modelAdapterResolver);
}