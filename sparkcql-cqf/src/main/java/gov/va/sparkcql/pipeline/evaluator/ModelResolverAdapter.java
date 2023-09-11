package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.pipeline.model.ModelAdapter;
import org.opencds.cqf.cql.engine.model.ModelResolver;

public class ModelResolverAdapter implements ModelResolver {

    private ModelAdapter modelAdapter;

    public ModelResolverAdapter(ModelAdapter modelAdapter) {
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
}