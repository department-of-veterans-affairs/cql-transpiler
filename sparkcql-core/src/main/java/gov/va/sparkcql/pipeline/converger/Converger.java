package gov.va.sparkcql.pipeline.converger;

import java.util.List;
import java.util.Map;

import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.pipeline.Component;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;

public interface Converger extends Component {

    public JavaPairRDD<String, Map<Retrieval, List<Object>>> converge(Map<Retrieval, JavaRDD<Object>> retrieveMap, Plan plan, ModelAdapterSet modelAdapterSet);
}