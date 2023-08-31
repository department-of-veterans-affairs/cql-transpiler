package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.configuration.Component;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.pipeline.model.ModelAdapterCollection;

public interface Retriever extends Component {

    public JavaRDD<Object> retrieve(Retrieval retrieval, ModelAdapterCollection modelAdapterCollection);
}