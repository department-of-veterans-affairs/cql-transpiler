package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.configuration.Component;
import org.apache.spark.api.java.JavaRDD;

import gov.va.sparkcql.pipeline.model.ModelAdapterComposite;

public interface Retriever extends Component {

    public JavaRDD<Object> retrieve(Retrieval retrieval, ModelAdapterComposite modelAdapterComposite);
}