package gov.va.sparkcql.service.modeladapter;

import java.io.Serializable;
import java.util.Map;

import gov.va.sparkcql.types.DataType;

public interface ModelAdapter extends Serializable {

    public String getNamespaceUri();

    public Object deserialize(DataType dataType, String json);

    public String serialize(Object entity);

    public Map<DataType, Class<?>> getDataTypeToClassMapping();
}