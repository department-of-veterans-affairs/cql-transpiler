package gov.va.sparkcql.adapter;

import java.util.Map;

import gov.va.sparkcql.types.DataType;

public interface ModelAdapter {

    public String getNamespaceUri();

    public Object deserialize(DataType dataType, String json);

    public String serialize(Object entity);

    public Map<DataType, Class<?>> getDataTypeToClassMapping();
}