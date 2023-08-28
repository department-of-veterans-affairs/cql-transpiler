package gov.va.sparkcql.pipeline.combiner;

import java.util.List;
import java.util.Map;

import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.pipeline.Component;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.model.ModelAdapterResolver;

public interface Combiner extends Component {

    public JavaPairRDD<String, Map<Retrieval, List<Object>>> combine(Map<Retrieval, JavaRDD<Object>> retrieveMap, Plan plan, ModelAdapterResolver modelAdapterResolver);
}