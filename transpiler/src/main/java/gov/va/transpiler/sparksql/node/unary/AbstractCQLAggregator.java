package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.AbstractNodeOneChild;

public abstract class AbstractCQLAggregator extends AbstractNodeOneChild {

    @Override
    public boolean isTable() { 
        return getChild().isTable();
    }
}
