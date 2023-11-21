package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Unary;

public class OperandDefNode extends Unary {

    private FunctionDefNode scope;

    public void setScope(FunctionDefNode scope) {
        this.scope = scope;
    }

    public AbstractCQLNode getParameterReplacementFromScope() {
        return scope.getParameterReplacementForOperandDef(this);
    }

    @Override
    public String asOneLine() {
        return getName() + " " + getChild().asOneLine();
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
