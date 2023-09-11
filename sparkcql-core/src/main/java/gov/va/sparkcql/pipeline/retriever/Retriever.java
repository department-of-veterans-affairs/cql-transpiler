package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.domain.RetrieveDefinition;
import gov.va.sparkcql.pipeline.Component;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.pipeline.model.ModelAdapterSet;

public interface Retriever extends Component {

    public JavaRDD<Object> retrieve(RetrieveDefinition retrieveDefinition, ModelAdapterSet modelAdapterSet);
}