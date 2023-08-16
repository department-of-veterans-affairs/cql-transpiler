package gov.va.sparkcql.model;

import java.io.Serializable;
import java.util.Set;

import com.google.inject.Inject;

import gov.va.sparkcql.entity.DataType;

public class ModelAdapterResolver implements Serializable {
    
    Set<ModelAdapter> modelAdapters;

    @Inject
    public ModelAdapterResolver(Set<ModelAdapter> modelAdapters) {
        this.modelAdapters = modelAdapters;
    }

    public <T> ModelAdapter resolveNamespace(String namespaceUri) {
        var adapter = modelAdapters.stream().filter(a -> a.getNamespaceUri().equals(namespaceUri));
        return adapter.findFirst().get();
    }

    public <T> ModelAdapter resolveNamespace(DataType dataType) {
        return resolveNamespace(dataType.getNamespaceUri());
    }

    public <T> ModelAdapter resolveType(DataType dataType) {
        var adapter = modelAdapters.stream().filter(a -> a.isTypeDefined(dataType));
        return adapter.findFirst().get();
    }
}