package gov.va.sparkcql.service.modeladapter;

import java.io.Serializable;
import java.util.Set;

import com.google.inject.Inject;

import gov.va.sparkcql.types.DataType;

public class ModelAdapterCollection implements Serializable {
    
    Set<ModelAdapter> modelAdapters;

    @Inject
    public ModelAdapterCollection(Set<ModelAdapter> modelAdapters) {
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
        var adapter = modelAdapters.stream().filter(a -> doesAdapterDefineType(dataType, a));
        return adapter.findFirst().get();
    }

    public Boolean doesAdapterDefineType(DataType dataType, ModelAdapter adapter) {
        for (var entry : adapter.getDataTypeToClassMapping().entrySet()) {
            if (entry.getKey().equals(dataType)) {
                return true;
            }
        }
        return false;
    }
}