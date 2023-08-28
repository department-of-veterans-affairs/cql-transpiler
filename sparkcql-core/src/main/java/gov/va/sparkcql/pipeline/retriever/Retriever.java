package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.pipeline.Component;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.pipeline.model.ModelAdapterResolver;

public interface Retriever extends Component {

    public JavaRDD<Object> retrieve(Retrieval retrieval, ModelAdapterResolver modelAdapterResolver);
}