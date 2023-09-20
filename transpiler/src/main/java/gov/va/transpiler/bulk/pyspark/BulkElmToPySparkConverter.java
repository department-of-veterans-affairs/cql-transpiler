package gov.va.transpiler.bulk.pyspark;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.AccessModifier;
import org.hl7.elm.r1.Add;
import org.hl7.elm.r1.Expression;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.ExpressionRef;
import org.hl7.elm.r1.FunctionDef;
import org.hl7.elm.r1.FunctionRef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Literal;

import gov.va.transpiler.ElmConverter;
import gov.va.transpiler.bulk.pyspark.output.AccessModifierNode;
import gov.va.transpiler.bulk.pyspark.output.ExpressionDefNode;
import gov.va.transpiler.bulk.pyspark.output.ExpressionNode;
import gov.va.transpiler.bulk.pyspark.output.expression.OperatorNode;
import gov.va.transpiler.bulk.pyspark.output.expression.ValueNode;
import gov.va.transpiler.output.DefaultOutputNode;
import gov.va.transpiler.output.OutputNode;

public class BulkElmToPySparkConverter extends ElmConverter<OutputNode, BulkElmToPySparkConverterState> {

    @Override
    public OutputNode convert(Library library, BulkElmToPySparkConverterState state) {
        return visitElement(library, state);
    }

    @Override
    public OutputNode visitLiteral(Literal literal, BulkElmToPySparkConverterState context) {
        return new ValueNode(literal.getValue());
    }

    @Override
    public OutputNode visitAdd(Add add, BulkElmToPySparkConverterState context) {
        var operatorNode = new OperatorNode("+");
        context.stack.push(operatorNode);
        return super.visitAdd(add, context);
    }

    @Override
    public OutputNode visitFunctionDef(FunctionDef functionDef, BulkElmToPySparkConverterState context) {
        // TODO
        return super.visitFunctionDef(functionDef, context);
    }

    @Override
    public OutputNode visitFunctionRef(FunctionRef functionRef, BulkElmToPySparkConverterState context) {
        // TODO
        return super.visitFunctionRef(functionRef, context);
    }

    @Override
    public OutputNode visitExpression(Expression expression, BulkElmToPySparkConverterState context) {
        var expressionNode = new ExpressionNode();
        context.stack.push(expressionNode);
        return super.visitExpression(expression, context);
    }

    @Override
    public OutputNode visitExpressionRef(ExpressionRef expressionRef, BulkElmToPySparkConverterState context) {
        if (!(expressionRef instanceof FunctionRef)) {
            return new ValueNode(expressionRef.getName());
        }
        return super.visitExpressionRef(expressionRef, context);
    }

    @Override
    public OutputNode visitExpressionDef(ExpressionDef expressionDef, BulkElmToPySparkConverterState context) {
        var expressionDefNode = new ExpressionDefNode();
        expressionDefNode.setName(expressionDef.getName());
        context.stack.push(expressionDefNode);
        return super.visitExpressionDef(expressionDef, context);
    }

    @Override
    public OutputNode visitAccessModifier(AccessModifier accessModifier, BulkElmToPySparkConverterState context) {
        return new AccessModifierNode();
    }

    @Override
    protected OutputNode defaultResult(Trackable elm, BulkElmToPySparkConverterState context) {
        if (!context.stack.isEmpty()) {
            return context.stack.pop();
        }
        return new DefaultOutputNode(defaultNodeName(elm));
    }

    @Override
    protected OutputNode aggregateResult(OutputNode aggregate, OutputNode nextResult) {
        if (nextResult != null) {
            aggregate.addChild(nextResult);
        }
        return aggregate;
    }

    private String defaultNodeName(Trackable elm) {
        return elm == null ? "NullElement" : elm.getClass().getSimpleName();
    }
}
