package gov.va.transpiler.bulk.sparksql;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.ElmConverterState;
import gov.va.transpiler.bulk.sparksql.node.ExpressionDefNode;
import gov.va.transpiler.node.OutputNode;

public class BulkElmToSparkSQLConverterState extends ElmConverterState {
    private final Stack<OutputNode<? extends Trackable>> stack = new Stack<>();
    private final Map<String, ExpressionDefNode> definedExpressions = new LinkedHashMap<>();

    private String cqlContext = null;

    public Stack<OutputNode<? extends Trackable>> getStack() {
        return stack;
    }

    public Map<String, ExpressionDefNode> getDefinedExpressions() {
        return definedExpressions;
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
