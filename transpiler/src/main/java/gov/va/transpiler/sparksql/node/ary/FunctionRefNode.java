package gov.va.transpiler.sparksql.node.ary;

import gov.va.transpiler.sparksql.node.Ary;
import gov.va.transpiler.sparksql.node.unary.FunctionDefNode;

public class FunctionRefNode extends Ary {

    private FunctionDefNode functionBeingReferenced;

    public FunctionDefNode getFunctionBeingReferenced() {
        return functionBeingReferenced;
    }

    public void setFunctionBeingReferenced(FunctionDefNode functionBeingReferenced) {
        this.functionBeingReferenced = functionBeingReferenced;
    }

    @Override
    public String asOneLine() {
        return getFunctionBeingReferenced().asInlineFunction(getChildren());
    }

    @Override
    public boolean isTable() {
        return functionBeingReferenced.isTable();
    }

    @Override
    public boolean isEncapsulated() {
        return functionBeingReferenced.isEncapsulated();
    }
}
