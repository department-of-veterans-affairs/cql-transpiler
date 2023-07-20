package gov.va.sparkcql.translator.result;

import java.util.List;

public abstract class Result {

    private List<Result> children;

    protected Result(List<Result> children) {
        this.children = children;
    }

    public Result(Result... children) {
        this.children = List.of(children);
    }

    public List<Result> children() {
        return children;
    }
}