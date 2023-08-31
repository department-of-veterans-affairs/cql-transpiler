package gov.va.sparkcql.pipeline.converger;

import java.util.List;
import java.util.Map;

import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.configuration.Component;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.model.ModelAdapterComposite;

public interface Converger extends Component {

    public JavaPairRDD<String, Map<Retrieval, List<Object>>> converge(Map<Retrieval, JavaRDD<Object>> retrieveMap, Plan plan, ModelAdapterComposite modelAdapterComposite);
}