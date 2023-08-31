package gov.va.sparkcql.pipeline.evaluator;

import org.opencds.cqf.cql.engine.data.DataProvider;
import org.opencds.cqf.cql.engine.model.ModelResolver;
import org.opencds.cqf.cql.engine.retrieve.RetrieveProvider;
import org.opencds.cqf.cql.engine.runtime.Code;
import org.opencds.cqf.cql.engine.runtime.Interval;

public class MutableCompositeDataProvider implements DataProvider {

    private ModelResolver modelResolver;
    private RetrieveProvider retrieveProvider;

    public MutableCompositeDataProvider(ModelResolver modelResolver, RetrieveProvider retrieveProvider) {
        this.modelResolver = modelResolver;
        this.retrieveProvider = retrieveProvider;
    }

    public void modelResolver(ModelResolver modelResolver) {
        this.modelResolver = modelResolver;
    }

    public void setRetrieveProviderAdapter(RetrieveProviderAdapter retrieveProviderAdapter) {
        this.retrieveProvider = retrieveProviderAdapter;
    }

    @Override
    public String getPackageName() {
        return this.modelResolver.getPackageName();
    }

    @Override
    public void setPackageName(String packageName) {
        this.modelResolver.setPackageName(packageName);
    }

    @Override
    public Object resolvePath(Object target, String path) {
        return this.modelResolver.resolvePath(target, path);
    }

    @Override
    public Object getContextPath(String contextType, String targetType) {
        return this.modelResolver.getContextPath(contextType, targetType);
    }

    @Override
    public Class<?> resolveType(String typeName) {
        return this.modelResolver.resolveType(typeName);
    }

    @Override
    public Class<?> resolveType(Object value) {
        return this.modelResolver.resolveType(value);
    }

    @Override
    public Boolean is(Object value, Class<?> type) {
        return this.modelResolver.is(value, type);
    }

    @Override
    public Object as(Object value, Class<?> type, boolean isStrict) {
        return this.modelResolver.as(value, type, isStrict);
    }

    @Override
    public Object createInstance(String typeName) {
        return this.modelResolver.createInstance(typeName);
    }

    @Override
    public void setValue(Object target, String path, Object value) {
        this.modelResolver.setValue(target, path, value);
    }

    @Override
    public Boolean objectEqual(Object left, Object right) {
        return this.modelResolver.objectEqual(left, right);
    }

    @Override
    public Boolean objectEquivalent(Object left, Object right) {
        return this.modelResolver.objectEquivalent(left, right);
    }

    @Override
    public Iterable<Object> retrieve(String context, String contextPath, Object contextValue, String dataType, String templateId, String codePath, Iterable<Code> codes, String valueSet, String datePath, String dateLowPath, String dateHighPath, Interval dateRange) {
        return this.retrieveProvider.retrieve(
                context,
                contextPath,
                contextValue,
                dataType,
                templateId,
                codePath,
                codes,
                valueSet,
                datePath,
                dateLowPath,
                dateHighPath,
                dateRange
                );
    }
}