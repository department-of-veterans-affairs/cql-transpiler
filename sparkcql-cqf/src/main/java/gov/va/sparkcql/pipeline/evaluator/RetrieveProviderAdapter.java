package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.RetrieveDefinition;
import org.opencds.cqf.cql.engine.retrieve.RetrieveProvider;
import org.opencds.cqf.cql.engine.runtime.Code;
import org.opencds.cqf.cql.engine.runtime.Interval;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RetrieveProviderAdapter implements RetrieveProvider {

    Map<RetrieveDefinition, List<Object>> clinicalData;

    public RetrieveProviderAdapter(Map<RetrieveDefinition, List<Object>> clinicalData) {
        this.clinicalData = clinicalData;
    }

    @Override
    public Iterable<Object> retrieve(String context, String contextPath, Object contextValue, String dataType, String templateId, String codePath, Iterable<Code> codes, String valueSet, String datePath, String dateLowPath, String dateHighPath, Interval dateRange) {

        // Find the retrieval based on the parameter values passed in from the engine. Both the engine and
        // optimizer are based on the same ELM Retrieve definitions but produce slightly different data structs.

        // TODO: Convert params to filter list and perform exact set based comparison

        var noPredicates = codes == null && valueSet == null && datePath == null && dateLowPath == null && dateHighPath == null && dateRange == null;
        var keyMatch = clinicalData.keySet().stream().filter(r -> {
            if (r.getDataType().getName().equals(dataType))
                if (r.getTemplateId().equals(templateId))
                    if (r.getFilters().isEmpty() && noPredicates) {
                        return true;
                    } else {
                        var conditionsMet = r.getFilters().stream().filter(f -> {
                            if (f.getComparator() == RetrieveDefinition.Comparator.IN) {
                                return f.getProperty().equals(codePath) && f.getValue().equals(valueSet);
                            }
                            return false;
                        });

                        // If any of the filters didn't meet the requirements, it's not a match.
                        return r.getFilters().size() == conditionsMet.count();
                    }

            return false;
        }).collect(Collectors.toList());

        if (keyMatch.size() > 1)
            throw new RuntimeException("Found multiple retrievals matching search condition.");
        if (keyMatch.isEmpty())
            throw new RuntimeException("Found no retrievals matching search condition.");

        var data = clinicalData.get(keyMatch.get(0));

        return new Iterable<Object>() {
            @Override
            public Iterator<Object> iterator() {
                return data.iterator();
            }
        };
    }
}