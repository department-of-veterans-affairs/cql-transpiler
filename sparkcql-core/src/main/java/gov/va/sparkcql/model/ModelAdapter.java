package gov.va.sparkcql.model;

import javax.xml.namespace.QName;

public interface ModelAdapter {

    public String getNamespaceUri();

    public Object deserialize(QName dataType, String json);

    public String serialize(Object entity);

    public Boolean isTypeDefined(QName dataType);
}