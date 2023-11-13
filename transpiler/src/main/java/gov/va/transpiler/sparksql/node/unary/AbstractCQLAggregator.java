package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.Unary;

public abstract class AbstractCQLAggregator extends Unary {

    @Override
    public boolean isTable() { 
        return getChild().isTable();
    }
}
