package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.model.ModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import org.cqframework.cql.cql2elm.CqlCompilerOptions;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.opencds.cqf.cql.engine.data.DataProvider;
import org.opencds.cqf.cql.engine.execution.CqlEngine;
import org.opencds.cqf.cql.engine.execution.Environment;
import org.opencds.cqf.cql.engine.fhir.model.R4FhirModelResolver;
import org.opencds.cqf.cql.engine.model.ModelResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CqfEvaluatorFactory implements EvaluatorFactory {

    @Override
    public Evaluator create(Configuration configuration, Plan plan, ModelAdapterSet modelAdapterSet, Object terminologyData) {
        return new CqfEvaluator(plan, modelAdapterSet, terminologyData);
    }
}