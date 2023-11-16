package gov.va.transpiler.sparksql.converter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.unary.ExpressionDefNode;
import gov.va.transpiler.sparksql.node.unary.FunctionDefNode;

public class State {
    private final Stack<AbstractCQLNode> stack = new Stack<>();
    private final Map<String, ExpressionDefNode> definedExpressions = new LinkedHashMap<>();
    private final Map<String, FunctionDefNode> definedFunctions = new LinkedHashMap<>();
    private final Stack<FunctionDefNode> functionStack = new Stack<>();

    private String cqlContext = null;

    public Stack<AbstractCQLNode> getStack() {
        return stack;
    }

    public Stack<FunctionDefNode> getFunctionStack() {
        return functionStack;
    }

    public Map<String, ExpressionDefNode> getDefinedExpressions() {
        return definedExpressions;
    }

    public Map<String, FunctionDefNode> getDefinedFunctions() {
        return definedFunctions;
    }

    public String getCqlContext() {
        return cqlContext;
    }

    public void setCqlContext(String cqlContext) {
        if ("Unfiltered".equalsIgnoreCase(cqlContext)) {
            this.cqlContext = null;
        } else {
            this.cqlContext = cqlContext;
        }
    }
}
