package gov.va.sparkcql.model;

import javax.xml.namespace.QName;

public interface Model {

    public String getNamespaceUri();

    public Object deserialize(QName dataType, String json);
}