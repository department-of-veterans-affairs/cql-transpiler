package gov.va.sparkcql.entity;

import java.util.List;

public class DataTypedList<T> {
    
    private DataType dataType;

    private List<T> values;

    public DataTypedList() {
        this.values = List.of();
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public DataTypedList<T> withDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public DataTypedList<T> withValues(List<T> values) {
        this.values = values;
        return this;
    }
}
