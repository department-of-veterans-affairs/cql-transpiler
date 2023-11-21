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
        OperandDefNode operandDefForRef = getOperandDefMatchingParameterFromScope();
        var parameterReplacement = operandDefForRef == null ? null : operandDefForRef.getParameterReplacementFromScope();
        return parameterReplacement == null ? (operandDefForRef == null ? getName() : operandDefForRef.getName()) : parameterReplacement.asOneLine();
    }

    @Override
    public boolean isTable() {
        OperandDefNode operandDefForRef = getOperandDefMatchingParameterFromScope();
        var parameterReplacement = operandDefForRef == null ? null : operandDefForRef.getParameterReplacementFromScope();
        return parameterReplacement == null ? (operandDefForRef == null ? false : operandDefForRef.isTable()) : parameterReplacement.isTable();
    }

    @Override
    public boolean isEncapsulated() {
        OperandDefNode operandDefForRef = getOperandDefMatchingParameterFromScope();
        var parameterReplacement = operandDefForRef == null ? null : operandDefForRef.getParameterReplacementFromScope();
        return parameterReplacement == null ? (operandDefForRef == null ? false : operandDefForRef.isEncapsulated()) : parameterReplacement.isEncapsulated();
    }

    @Override
    public boolean isColumnReference() {
        OperandDefNode operandDefForRef = getOperandDefMatchingParameterFromScope();
        var parameterReplacement = operandDefForRef == null ? null : operandDefForRef.getParameterReplacementFromScope();
        return parameterReplacement == null ? (operandDefForRef == null ? false : operandDefForRef.isColumnReference()) : parameterReplacement.isColumnReference();    }
}
