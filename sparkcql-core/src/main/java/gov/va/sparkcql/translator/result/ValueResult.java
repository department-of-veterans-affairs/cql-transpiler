package gov.va.sparkcql.translator.result;

import java.util.List;

public class ValueResult extends Result {
    
    private Object value;

    public ValueResult(List<Result> children, Object value) {
        super(children);
        this.value = value;
    }

    public ValueResult(Object value) {
        this(null, value);
    }

    public Object value() {
        return this.value;
    }
}