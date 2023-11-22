package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Leaf;
import gov.va.transpiler.sparksql.node.unary.FunctionDefNode;

public class OperandDefNode extends Leaf {

    private FunctionDefNode scope;
    private AbstractCQLNode type;

    public void setType(AbstractCQLNode type) {
        this.type = type;
    }

    public void setScope(FunctionDefNode scope) {
        this.scope = scope;
    }

    public AbstractCQLNode getParameterReplacementFromScope() {
        return scope == null ? null : scope.getParameterReplacementForOperandDef(this);
    }

    @Override
    public String asOneLine() {
        return getName() + " " + type.asOneLine();
    }

    @Override
    public boolean isTable() {
        var replacement = getParameterReplacementFromScope();
        return replacement == null ? false : replacement.isTable();
    }

    @Override
    public boolean isEncapsulated() {
        var replacement = getParameterReplacementFromScope();
        return replacement == null ? false : replacement.isEncapsulated();
    }

    @Override
    public boolean isColumnReference() {
        var replacement = getParameterReplacementFromScope();
        return replacement == null ? false : replacement.isColumnReference();
    }
}
