package gov.va.transpiler.bulk.sparksql;

import java.util.Stack;

import gov.va.transpiler.ElmConverterState;
import gov.va.transpiler.node.OutputNode;

public class BulkElmToSparkSQLConverterState extends ElmConverterState {
    private final Stack<OutputNode> stack = new Stack<>();
    private final Stack<Boolean> compressed = new Stack<>();

    public Stack<OutputNode> getStack() {
        return stack;
    }

    public Stack<Boolean> getCompressed() {
        return compressed;
    }
}
