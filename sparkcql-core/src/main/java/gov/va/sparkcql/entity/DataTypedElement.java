package gov.va.sparkcql.entity;

public class DataTypedElement<T> {
    
    private DataType dataType;

    private T value;

    public DataTypedElement() {
        this.value = null;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public DataTypedElement<T> withDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public DataTypedElement<T> withValues(T value) {
        this.value = value;
        return this;
    }
}
