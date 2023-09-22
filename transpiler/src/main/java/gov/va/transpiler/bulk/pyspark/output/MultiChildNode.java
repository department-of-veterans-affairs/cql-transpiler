package gov.va.transpiler.bulk.pyspark.output;

import java.util.ArrayList;
import java.util.List;

import gov.va.transpiler.output.OutputNode;

public abstract class MultiChildNode extends OutputNode {

    private List<OutputNode> children = new ArrayList<>();

    @Override
    public boolean addChild(OutputNode child) {
        children.add(child);
        return true;
    }

    protected List<OutputNode> getChildren() {
        return children;
    }
}
