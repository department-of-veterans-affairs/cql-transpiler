package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.pipeline.model.ModelAdapterResolver;

public interface EvaluatorFactory {

    public Evaluator create(LibraryCollection libraryCollection, ModelAdapterResolver modelAdapterResolver, Object terminologyData);
}