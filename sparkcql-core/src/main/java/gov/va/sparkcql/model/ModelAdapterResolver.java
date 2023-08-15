package gov.va.sparkcql.model;

import java.io.Serializable;
import java.util.Set;

import javax.xml.namespace.QName;

import com.google.inject.Inject;

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

    public <T> ModelAdapter resolveNamespace(QName dataType) {
        return resolveNamespace(dataType.getNamespaceURI());
    }

    public <T> ModelAdapter resolveType(QName dataType) {
        var adapter = modelAdapters.stream().filter(a -> a.isTypeDefined(dataType));
        return adapter.findFirst().get();
    }
}