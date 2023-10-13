package gov.va.transpiler.bulk.pyspark;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.*;

import gov.va.transpiler.ElmConverter;
import gov.va.transpiler.bulk.pyspark.node.AccessModifierNode;
import gov.va.transpiler.bulk.pyspark.node.ContextDefNode;
import gov.va.transpiler.bulk.pyspark.node.ExpressionDefNode;
import gov.va.transpiler.bulk.pyspark.node.ExpressionRefNode;
import gov.va.transpiler.bulk.pyspark.node.LibraryNode;
import gov.va.transpiler.bulk.pyspark.node.OperatorNode;
import gov.va.transpiler.bulk.pyspark.node.PropertyNode;
import gov.va.transpiler.bulk.pyspark.node.RetrieveNode;
import gov.va.transpiler.bulk.pyspark.node.SingletonFromNode;
import gov.va.transpiler.bulk.pyspark.node.TupleElementNode;
import gov.va.transpiler.bulk.pyspark.node.TupleNode;
import gov.va.transpiler.bulk.pyspark.node.LiteralNode;
import gov.va.transpiler.bulk.pyspark.utilities.CQLNameToPythonName;
import gov.va.transpiler.bulk.pyspark.utilities.CQLTypeToPythonType;
import gov.va.transpiler.node.DefaultOutputNode;
import gov.va.transpiler.node.OutputNode;

public class BulkElmToPySparkConverter extends ElmConverter<OutputNode, BulkElmToPySparkConverterState> {

    private final CQLTypeToPythonType cqlTypeToPythonType;
    private final CQLNameToPythonName cqlNameToPythonName;

    public BulkElmToPySparkConverter(CQLTypeToPythonType cqlTypeToPythonType, CQLNameToPythonName cqlNameToPythonName) {
        this.cqlTypeToPythonType = cqlTypeToPythonType;
        this.cqlNameToPythonName = cqlNameToPythonName;
    }

    @Override
    public OutputNode convert(Library library, BulkElmToPySparkConverterState state) {
        return visitElement(library, state);
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

    @Override
    public OutputNode visitLibrary(Library library, BulkElmToPySparkConverterState context) {
        var currentNode = new LibraryNode();
        currentNode.setName(currentNode.getFileNameFromLibrary(library));
        currentNode.setCqlNodeEquivalent(library);
        context.getStack().push(currentNode);
        OutputNode result = super.visitLibrary(library, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitExpressionRef(ExpressionRef expressionRef, BulkElmToPySparkConverterState context) {
        if (!(expressionRef instanceof FunctionRef)) {
            var expressionRefNode = new ExpressionRefNode(cqlNameToPythonName);
            expressionRefNode.setName(expressionRef.getName());
            return expressionRefNode;
        }
        return super.visitExpressionRef(expressionRef, context);
    }

    @Override
    public OutputNode visitExpressionDef(ExpressionDef expressionDef, BulkElmToPySparkConverterState context) {
        var currentNode = new ExpressionDefNode(cqlNameToPythonName);
        currentNode.setName(expressionDef.getName());
        currentNode.setCqlNodeEquivalent(expressionDef);
        context.getStack().push(currentNode);
        OutputNode result = super.visitExpressionDef(expressionDef, context);
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
    public OutputNode visitTupleElement(TupleElement tupleElement, BulkElmToPySparkConverterState context) {
        var currentNode = new TupleElementNode();
        currentNode.setCqlNodeEquivalent(tupleElement);
        currentNode.setName(tupleElement.getName());
        context.getStack().push(currentNode);
        OutputNode result = super.visitTupleElement(tupleElement, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitProperty(Property property, BulkElmToPySparkConverterState context) {
        var currentNode = new PropertyNode(cqlNameToPythonName);
        currentNode.setCqlNodeEquivalent(property);
        currentNode.setName(property.getPath());
        currentNode.setScope(property.getScope());
        currentNode.setSource(property.getSource());
        context.getStack().push(currentNode);
        OutputNode result = super.visitProperty(property, context);
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
    public OutputNode visitLiteral(Literal literal, BulkElmToPySparkConverterState context) {
        var currentNode = new LiteralNode(cqlTypeToPythonType);
        currentNode.setName(literal.getValue());
        currentNode.setResultType(literal.getResultType().toString());
        currentNode.setCqlNodeEquivalent(literal);
        return currentNode;
    }

    @Override
    public OutputNode visitContextDef(ContextDef contextDef, BulkElmToPySparkConverterState context) {
        var currentNode = new ContextDefNode(contextDef.getName());
        currentNode.setCqlNodeEquivalent(contextDef);
        return currentNode;
    }

    @Override
    public OutputNode visitRetrieve(Retrieve retrieve, BulkElmToPySparkConverterState context) {
        var currentNode = new RetrieveNode(retrieve.getDataType().getLocalPart(), retrieve.getDataType().getNamespaceURI());
        currentNode.setCqlNodeEquivalent(retrieve);
        return currentNode;
    }

    @Override
    public OutputNode visitSingletonFrom(SingletonFrom singletonFrom, BulkElmToPySparkConverterState context) {
        var currentNode = new SingletonFromNode();
        currentNode.setCqlNodeEquivalent(singletonFrom);
        context.getStack().push(currentNode);
        OutputNode result = super.visitSingletonFrom(singletonFrom, context);
        context.getStack().pop();
        return result;
    }

    private String defaultNodeName(Trackable elm) {
        return elm == null ? "NullElement" : elm.getClass().getSimpleName();
    }
}
