package gov.va.sparkcql.pipeline.modeladapter;

import java.io.Serializable;
import java.util.Set;

import com.google.inject.Inject;

import gov.va.sparkcql.pipeline.Stage;
import gov.va.sparkcql.types.DataType;

public class ModelAdapterResolver implements Stage {
    
    Set<ModelAdapter> modelAdapters;

    @Inject
    public ModelAdapterResolver(Set<ModelAdapter> modelAdapters) {
        this.modelAdapters = modelAdapters;
    }

    public <T> ModelAdapter forNamespace(String namespaceUri) {
        var adapter = modelAdapters.stream().filter(a -> a.getNamespaceUri().equals(namespaceUri));
        return adapter.findFirst().get();
    }

    public <T> ModelAdapter forNamespace(DataType dataType) {
        return forNamespace(dataType.getNamespaceUri());
    }

    public <T> ModelAdapter forType(DataType dataType) {
        var adapter = modelAdapters.stream().filter(a -> a.isDataTypeSupported(dataType));
        return adapter.findFirst().get();
    }
}