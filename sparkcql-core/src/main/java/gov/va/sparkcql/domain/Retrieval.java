package gov.va.sparkcql.domain;

import java.io.Serializable;
import java.util.List;

import gov.va.sparkcql.types.DataType;
import org.hl7.elm.r1.Retrieve;

public class Retrieval implements Serializable {

    private DataType dataType;

    private List<Object> operations;

    public Retrieval() {
    }

    public static <T> Retrieval of(Retrieve retrieve) {
        var r = new Retrieval();
        r.setDataType(new DataType(retrieve.getDataType()));
        r.operations = List.of();
        // TODO: Convert Retrieve to Retrieval
        return r;
    }

    enum Comparator {
        EQUALS,
        NOT_EQUALS,
        GREATER_THAN,
        GREATER_THAN_EQUALS,
        LESS_THAN,
        LESS_THAN_EQUALS,
        IN,
        NOT_IN
    }

    static class Filter {

        private String property;

        private Comparator comparator;

        private Object value;

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public Comparator getComparator() {
            return comparator;
        }

        public void setComparator(Comparator comparator) {
            this.comparator = comparator;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Retrieval withDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public List<Object> getOperations() {
        return operations;
    }

    public void setOperations(List<Object> operations) {
        this.operations = operations;
    }
}