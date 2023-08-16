package gov.va.sparkcql.types;

public class DataTypedValue<T> {
    
    private DataType dataType;

    private T value;

    public DataTypedValue() {
        this.value = null;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public DataTypedValue<T> withDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public DataTypedValue<T> withValues(T value) {
        this.value = value;
        return this;
    }
}
