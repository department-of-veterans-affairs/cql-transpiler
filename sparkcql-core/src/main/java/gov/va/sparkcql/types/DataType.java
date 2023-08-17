package gov.va.sparkcql.types;

import javax.xml.namespace.QName;

public class DataType {
    
    private String namespaceUri = "";

    private String name = "";

    public DataType() {
    }

    public DataType(QName dataType) {
        this.namespaceUri = dataType.getNamespaceURI();
        this.name = dataType.getLocalPart();
    }

    public DataType(String namespaceUri, String name) {
        this.namespaceUri = namespaceUri;
        this.name = name;
    }

    public String getNamespaceUri() {
        return namespaceUri;
    }

    public void setNamespaceUri(String namespaceUri) {
        this.namespaceUri = namespaceUri;
    }

    public DataType withNamespaceUri(String namespaceUri) {
        this.namespaceUri = namespaceUri;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return this.namespaceUri.equals("") ? this.name : "{" + this.namespaceUri + "}" + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        var dataType = (DataType)o;
        return this.namespaceUri.equals(dataType.namespaceUri) && this.name.equals(dataType.name);
    }
}
