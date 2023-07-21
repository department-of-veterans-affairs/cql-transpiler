package gov.va.sparkcql.translator.result;

import gov.va.sparkcql.translator.Cardinality;

public abstract class Result {

    private Result[] children;
    private Cardinality cardinality = Cardinality.OneToOne;

    protected Result() {
    }

    protected Result(Result from) {
        this.cardinality = from.cardinality;
    }

    public Result with(Result[] children) {
        this.children = children;
        return this;
    }

    public Result with(Result child) {
        this.children = new Result[]{ child };
        return this;
    }

    protected Result with(Cardinality cardinality) {
        this.cardinality = cardinality;
        return this;
    }

    public Result[] children() {
        return children;
    }

    public Cardinality cardinality() {
        return cardinality;
    }
}