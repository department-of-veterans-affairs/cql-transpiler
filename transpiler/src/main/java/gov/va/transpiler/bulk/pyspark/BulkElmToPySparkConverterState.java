package gov.va.transpiler.bulk.pyspark;

import java.util.Stack;

import gov.va.transpiler.ElmConverterState;
import gov.va.transpiler.node.OutputNode;

public class BulkElmToPySparkConverterState extends ElmConverterState {
    private final Stack<OutputNode> stack = new Stack<>();

    public Stack<OutputNode> getStack() {
        return stack;
    }
}
