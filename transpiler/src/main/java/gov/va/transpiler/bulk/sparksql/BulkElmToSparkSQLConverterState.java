package gov.va.transpiler.bulk.sparksql;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

import gov.va.transpiler.ElmConverterState;
import gov.va.transpiler.bulk.sparksql.node.ExpressionDefNode;
import gov.va.transpiler.node.OutputNode;

public class BulkElmToSparkSQLConverterState extends ElmConverterState {
    private final Stack<OutputNode> stack = new Stack<>();
    private final Map<String, ExpressionDefNode> definedExpressions = new LinkedHashMap<>();
    public Stack<OutputNode> getStack() {
        return stack;
    }

    public Map<String, ExpressionDefNode> getDefinedExpressions() {
        return definedExpressions;
    }
}
