package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.AbstractNodeNoChildren;

public class ContextDefNode extends AbstractNodeNoChildren {

    @Override
    public String asOneLine() {
        return "-- Context: " + getName();
    }
}
