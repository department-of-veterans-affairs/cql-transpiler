package gov.va.sparkcql.model;

import gov.va.sparkcql.entity.DataType;

public interface ModelAdapter {

    public String getNamespaceUri();

    public Object deserialize(DataType dataType, String json);

    public String serialize(Object entity);

    public Boolean isTypeDefined(DataType dataType);
}