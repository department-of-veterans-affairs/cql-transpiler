package gov.va.sparkcql.pipeline.combiner;

import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.domain.RetrievalOperation;
import gov.va.sparkcql.pipeline.Component;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;

public interface Combiner extends Component {

    public JavaPairRDD<String, List<Object>> combine(Map<RetrievalOperation, JavaRDD<Object>> retrieveMap, Plan plan, ModelAdapterResolver modelAdapterResolver);
}
