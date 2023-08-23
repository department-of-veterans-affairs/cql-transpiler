package gov.va.sparkcql.pipeline.retriever;

import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.domain.RetrievalOperation;
import gov.va.sparkcql.pipeline.Component;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;

public interface Retriever extends Component {

    public JavaRDD<Object> retrieve(RetrievalOperation retrievalOperation, ModelAdapterResolver modelAdapterResolver, Object terminologyProvider);
}