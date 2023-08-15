package gov.va.sparkcql.entity;

import javax.xml.namespace.QName;

public class GenericTypeSpecifiedElement<T> {
    
    private QName resultTypeName;
    
    private T resultValue;

    public QName getResultTypeName() {
        return resultTypeName;
    }

    public void setResultTypeName(QName resultTypeName) {
        this.resultTypeName = resultTypeName;
    }

    public GenericTypeSpecifiedElement<T> withResultTypeName(QName resultTypeName) {
        this.resultTypeName = resultTypeName;
        return this;
    }

    public T getResultValue() {
        return resultValue;
    }

    public void setResultValue(T resultValue) {
        this.resultValue = resultValue;
    }

    public GenericTypeSpecifiedElement<T> withResultValue(T resultValue) {
        this.resultValue = resultValue;
        return this;
    }
}