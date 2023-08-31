package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.Retrieval;
import org.opencds.cqf.cql.engine.retrieve.RetrieveProvider;
import org.opencds.cqf.cql.engine.runtime.Code;
import org.opencds.cqf.cql.engine.runtime.Interval;

import java.util.List;
import java.util.Map;

public class RetrieveProviderAdapter implements RetrieveProvider {

    Map<Retrieval, List<Object>> clinicalData;

    public RetrieveProviderAdapter(Map<Retrieval, List<Object>> clinicalData) {
        this.clinicalData = clinicalData;
    }

    @Override
    public Iterable<Object> retrieve(String context, String contextPath, Object contextValue, String dataType, String templateId, String codePath, Iterable<Code> codes, String valueSet, String datePath, String dateLowPath, String dateHighPath, Interval dateRange) {
        throw new RuntimeException("@@@ NEXT IMPLEMENTATION @@@");
    }
}