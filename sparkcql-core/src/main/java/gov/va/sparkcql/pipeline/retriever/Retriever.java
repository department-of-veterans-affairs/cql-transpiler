package gov.va.sparkcql.pipeline.retriever;

import org.apache.spark.api.java.JavaRDD;
import org.hl7.elm.r1.Retrieve;

import gov.va.sparkcql.pipeline.Component;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;

public interface Retriever extends Component {

    public JavaRDD<Object> retrieve(Retrieve retrieve, ModelAdapterResolver modelAdapterResolver);
}