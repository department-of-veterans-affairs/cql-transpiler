package gov.va.sparkcql.pipeline.model;

import java.util.List;
import java.util.stream.Collectors;

import gov.va.sparkcql.configuration.Component;
import gov.va.sparkcql.types.DataType;

public class ModelAdapterSet implements Component {

    List<ModelAdapter> modelAdapters;

    public ModelAdapterSet(List<ModelAdapter> modelAdapters) {
        this.modelAdapters = modelAdapters;
    }

    public List<String> getNamespaces() {
        return modelAdapters.stream()
                .map(ModelAdapter::getNamespaceUri).collect(Collectors.toList());
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
        return adapter.findFirst()
                .orElseThrow(() -> new RuntimeException("DataType " + dataType.toString() + " not found."));
    }
}