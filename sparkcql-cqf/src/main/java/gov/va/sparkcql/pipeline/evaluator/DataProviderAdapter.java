package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.pipeline.model.ModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterComposite;
import org.opencds.cqf.cql.engine.data.DataProvider;
import org.opencds.cqf.cql.engine.runtime.Code;
import org.opencds.cqf.cql.engine.runtime.Interval;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataProviderAdapter {

    private final Map<String, DataProvider> dataProviders;

    public DataProviderAdapter(ModelAdapterComposite modelAdapterComposite) {
        dataProviders = modelAdapterComposite.getNamespaces().stream()
                .collect(Collectors.toMap(
                        k -> k,
                        v -> {
                            var modelAdapter = modelAdapterComposite.forNamespace(v);
                            return new InternalDataProvider(modelAdapter);
                        }));
    }

    public Map<String, DataProvider> getDataProviderMap() {
        return this.dataProviders;
    }

    public void setClinicalData(Map<Retrieval, List<Object>> clinicalData) {
    }

    static class InternalDataProvider implements DataProvider {

        private ModelAdapter modelAdapter;

        public InternalDataProvider(ModelAdapter modelAdapter) {
            this.modelAdapter = modelAdapter;
        }

        @Override
        public String getPackageName() {
            return null;
        }

        @Override
        public void setPackageName(String packageName) {

        }

        @Override
        public Object resolvePath(Object target, String path) {
            return null;
        }

        @Override
        public Object getContextPath(String contextType, String targetType) {
            return null;
        }

        @Override
        public Class<?> resolveType(String typeName) {
            return null;
        }

        @Override
        public Class<?> resolveType(Object value) {
            return null;
        }

        @Override
        public Boolean is(Object value, Class<?> type) {
            return null;
        }

        @Override
        public Object as(Object value, Class<?> type, boolean isStrict) {
            return null;
        }

        @Override
        public Object createInstance(String typeName) {
            return null;
        }

        @Override
        public void setValue(Object target, String path, Object value) {

        }

        @Override
        public Boolean objectEqual(Object left, Object right) {
            return null;
        }

        @Override
        public Boolean objectEquivalent(Object left, Object right) {
            return null;
        }

        @Override
        public Iterable<Object> retrieve(String context, String contextPath, Object contextValue, String dataType, String templateId, String codePath, Iterable<Code> codes, String valueSet, String datePath, String dateLowPath, String dateHighPath, Interval dateRange) {
            return null;
        }
    }
}