package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.Leaf;
import gov.va.transpiler.sparksql.node.unary.FunctionDefNode;
import gov.va.transpiler.sparksql.node.unary.OperandDefNode;

public class OperandRefNode extends Leaf {

    private FunctionDefNode scope;

    public void setScope(FunctionDefNode scope) {
        this.scope = scope;
    }

    public OperandDefNode getOperandDefMatchingParameterFromScope() {
        return scope.getOperandDefForName(getName());
    }

    @Override
    public String asOneLine() {
        return getName();
    }
}
