package gov.va.sparkcql.service.adapter;

import gov.va.sparkcql.types.DataType;

public interface ModelAdapter {

    public String getNamespaceUri();

    public Object deserialize(DataType dataType, String json);

    public String serialize(Object entity);

    public Boolean isTypeDefined(DataType dataType);
}