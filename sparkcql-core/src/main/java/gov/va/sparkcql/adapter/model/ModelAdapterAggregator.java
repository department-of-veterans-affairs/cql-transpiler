package gov.va.sparkcql.adapter.model;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.spark.sql.types.StructType;

public class ModelAdapterAggregator implements ModelAdapter {

    protected List<ModelAdapter> modelAdapters;

    public ModelAdapterAggregator(List<ModelAdapter> modelAdapters) {
        this.modelAdapters = modelAdapters;
    }

    ModelAdapter resolveModel(QName dataType) {
        var resolvedAdapter = modelAdapters.stream().filter(a -> a.supportedDataTypes().contains(dataType));
        if (resolvedAdapter.count() > 0) {
            return resolvedAdapter.findFirst().get();
        } else {
            throw new RuntimeException("No ModelAdapter found for data type " + dataType.toString() + ".");
        }
    }

    @Override
    public String namespaceUri() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<QName> supportedDataTypes() {
        return this.modelAdapters.stream().flatMap(adapter -> {
            return adapter.supportedDataTypes().stream();
        }).distinct().toList();
    }

    @Override
    public StructType schemaOf(QName dataType) {
        return resolveModel(dataType).schemaOf(dataType);
    }    
}
