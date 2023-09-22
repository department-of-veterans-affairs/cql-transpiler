package gov.va.transpiler.bulk.pyspark;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.*;

import gov.va.transpiler.ElmConverter;
import gov.va.transpiler.bulk.pyspark.output.*;
import gov.va.transpiler.output.DefaultOutputNode;
import gov.va.transpiler.output.OutputNode;

public class BulkElmToPySparkConverter extends ElmConverter<OutputNode, BulkElmToPySparkConverterState> {

    @Override
    public OutputNode convert(Library library, BulkElmToPySparkConverterState state) {
        return visitElement(library, state);
    }

    @Override
    public OutputNode visitLiteral(Literal literal, BulkElmToPySparkConverterState context) {
        var currentNode = new ValueNode();
        currentNode.setValue(literal.getValue());
        currentNode.setPythonDataType(ValueNode.getMatchingPythonDataType(literal.getResultType().toString()));
        currentNode.setCqlNodeEquivalent(literal);
        return currentNode;
    }

    @Override
    public OutputNode visitTupleElement(TupleElement tupleElement, BulkElmToPySparkConverterState context) {
        var currentNode = new TupleElementNode();
        currentNode.setCqlNodeEquivalent(tupleElement);
        currentNode.setName(new VariableNameNode(tupleElement.getName()));
        currentNode.setType(tupleElement.getResultType().toString());
        context.getStack().push(currentNode);
        OutputNode result = super.visitTupleElement(tupleElement, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitTuple(Tuple tuple, BulkElmToPySparkConverterState context) {
        var currentNode = new TupleNode();
        currentNode.setCqlNodeEquivalent(tuple);
        context.getStack().push(currentNode);
        OutputNode result = super.visitTuple(tuple, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitAdd(Add add, BulkElmToPySparkConverterState context) {
        var currentNode = new OperatorNode("+");
        currentNode.setCqlNodeEquivalent(add);
        context.getStack().push(currentNode);
        OutputNode result = super.visitAdd(add, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitExpressionRef(ExpressionRef expressionRef, BulkElmToPySparkConverterState context) {
        if (!(expressionRef instanceof FunctionRef)) {
            return new VariableNameNode(expressionRef.getName());
        }
        return super.visitExpressionRef(expressionRef, context);
    }

    @Override
    public OutputNode visitExpressionDef(ExpressionDef expressionDef, BulkElmToPySparkConverterState context) {
        var currentNode = new ExpressionDefNode();
        currentNode.setName(new VariableNameNode(expressionDef.getName()));
        currentNode.setCqlNodeEquivalent(expressionDef);
        context.getStack().push(currentNode);
        OutputNode result = super.visitExpressionDef(expressionDef, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitAccessModifier(AccessModifier accessModifier, BulkElmToPySparkConverterState context) {
        var currentNode = new AccessModifierNode();
        currentNode.setCqlNodeEquivalent(accessModifier);
        return currentNode;
    }

    @Override
    protected OutputNode defaultResult(Trackable elm, BulkElmToPySparkConverterState context) {
        OutputNode stackLocation;
        if (!context.getStack().empty() && elm == (stackLocation = context.getStack().peek()).getCqlNodeEquivalent()) {
            return stackLocation;
        }
        stackLocation = new DefaultOutputNode(defaultNodeName(elm));
        context.getStack().push(stackLocation);
        return stackLocation;
    }

    @Override
    protected OutputNode aggregateResult(OutputNode aggregate, OutputNode nextResult) {
        if (nextResult != null) {
            boolean added = aggregate.addChild(nextResult);
            if (added == false) {
                throw new IllegalAccessError("Tried to add invalid child [" + nextResult + "] to node [" + aggregate + "].");
            }
        }
        return aggregate;
    }

    private String defaultNodeName(Trackable elm) {
        return elm == null ? "NullElement" : elm.getClass().getSimpleName();
    }
}
