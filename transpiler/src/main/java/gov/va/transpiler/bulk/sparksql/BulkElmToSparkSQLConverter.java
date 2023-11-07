package gov.va.transpiler.bulk.sparksql;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.*;

import gov.va.transpiler.ElmConverter;
import gov.va.transpiler.bulk.sparksql.node.*;
import gov.va.transpiler.bulk.sparksql.utilities.CQLNameToSparkSQLName;
import gov.va.transpiler.bulk.sparksql.utilities.CQLTypeToSparkSQLType;
import gov.va.transpiler.node.OutputNode;

public class BulkElmToSparkSQLConverter extends ElmConverter<OutputNode<? extends Trackable>, BulkElmToSparkSQLConverterState> {

    private final CQLTypeToSparkSQLType cqlTypeToSparkSQLType;
    private final CQLNameToSparkSQLName cqlNameToSparkSQLName;

    public BulkElmToSparkSQLConverter(CQLTypeToSparkSQLType cqlTypeToSparkSQLType, CQLNameToSparkSQLName cqlNameToSparkSQLName) {
        this.cqlTypeToSparkSQLType = cqlTypeToSparkSQLType;
        this.cqlNameToSparkSQLName = cqlNameToSparkSQLName;
    }

    @Override
    public OutputNode<? extends Trackable> convert(Library library, BulkElmToSparkSQLConverterState state) {
        return visitElement(library, state);
    }

    @Override
    protected OutputNode<? extends Trackable> defaultResult(Trackable elm, BulkElmToSparkSQLConverterState context) {
        if (!context.getStack().empty() && elm == context.getStack().peek().getCqlNodeEquivalent()) {
            return context.getStack().peek();
        }
        OutputNode<Trackable> placeholder = new DefaultOutputNode();
        placeholder.setName(defaultNodeName(elm));
        placeholder.setCqlNodeEquivalent(elm);
        context.getStack().push(placeholder);
        return placeholder;
    }

    @Override
    protected OutputNode<? extends Trackable> aggregateResult(OutputNode<? extends Trackable> aggregate, OutputNode<? extends Trackable> nextResult) {
        if (nextResult != null) {
            boolean added = aggregate.addChild(nextResult);
            if (added == false) {
                throw new IllegalAccessError("Tried to add invalid child [" + nextResult + "] to node [" + aggregate + "].");
            }
        }
        return aggregate;
    }

    protected String defaultNodeName(Trackable elm) {
        return elm == null ? "NullElement" : elm.getClass().getSimpleName();
    }

    @Override
    public OutputNode<? extends Trackable> visitLibrary(Library library, BulkElmToSparkSQLConverterState context) {
        var currentNode = new LibraryNode();
        currentNode.setName(currentNode.getFileNameFromLibrary(library));
        currentNode.setCqlNodeEquivalent(library);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitLibrary(library, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitUsingDef(UsingDef usingDef, BulkElmToSparkSQLConverterState context) {
        var currentNode = new UsingDefNode();
        currentNode.setCqlNodeEquivalent(usingDef);
        return currentNode;
    }

    @Override
    public OutputNode<? extends Trackable> visitLiteral(Literal literal, BulkElmToSparkSQLConverterState context) {
        var currentNode = new LiteralNode(cqlTypeToSparkSQLType);
        currentNode.setCqlNodeEquivalent(literal);
        currentNode.setName(literal.getValue());
        currentNode.setResultType(literal.getResultType().toString());
        currentNode.setCqlNodeEquivalent(literal);
        return currentNode;
    }

    @Override
    public OutputNode<? extends Trackable> visitAccessModifier(AccessModifier accessModifier, BulkElmToSparkSQLConverterState context) {
        var currentNode = new AccessModifierNode();
        return currentNode;
    }

    @Override
    public OutputNode<? extends Trackable> visitExpressionDef(ExpressionDef expressionDef, BulkElmToSparkSQLConverterState context) {
        var currentNode = new ExpressionDefNode(cqlNameToSparkSQLName);
        currentNode.setName(expressionDef.getName());
        currentNode.setCqlNodeEquivalent(expressionDef);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitExpressionDef(expressionDef, context);
        context.getStack().pop();
        context.getDefinedExpressions().put(currentNode.getName(), currentNode);
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitExpressionRef(ExpressionRef expressionRef, BulkElmToSparkSQLConverterState context) {
        if (!(expressionRef instanceof FunctionRef)) {
            var expressionRefNode = new ExpressionRefNode();
            expressionRefNode.setName(expressionRef.getName());
            expressionRefNode.setCqlNodeEquivalent(expressionRef);
            expressionRefNode.setTable(context.getDefinedExpressions().get(expressionRefNode.getName()).isTable());
            return expressionRefNode;
        }
        return super.visitExpressionRef(expressionRef, context);
    }

    @Override
    public OutputNode<? extends Trackable> visitList(List list, BulkElmToSparkSQLConverterState context) {
        var currentNode = new ListNode();
        currentNode.setCqlNodeEquivalent(list);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitList(list, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitTupleElement(TupleElement tupleElement, BulkElmToSparkSQLConverterState context) {
        var currentNode = new TupleElementNode();
        currentNode.setCqlNodeEquivalent(tupleElement);
        currentNode.setName(tupleElement.getName());
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitTupleElement(tupleElement, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitTuple(Tuple tuple, BulkElmToSparkSQLConverterState context) {
        var currentNode = new TupleNode();
        currentNode.setCqlNodeEquivalent(tuple);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitTuple(tuple, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitRetrieve(Retrieve retrieve, BulkElmToSparkSQLConverterState context) {
        var currentNode = new RetrieveNode();
        // TODO: We'll worry about retrieve.getDataType().getNamespaceURI() later
        currentNode.setName(retrieve.getDataType().getLocalPart());
        currentNode.setCqlNodeEquivalent(retrieve);
        return currentNode;
    }

    @Override
    public OutputNode<? extends Trackable> visitProperty(Property property, BulkElmToSparkSQLConverterState context) {
        var currentNode = new PropertyNode();
        currentNode.setCqlNodeEquivalent(property);
        currentNode.setName(property.getPath());
        currentNode.setScope(property.getScope());
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitProperty(property, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitQuery(Query query, BulkElmToSparkSQLConverterState context) {
        var currentNode = new QueryNode();
        currentNode.setCqlNodeEquivalent(query);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitQuery(query, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitAliasedQuerySource(AliasedQuerySource aliasedQuerySource, BulkElmToSparkSQLConverterState context) {
        var currentNode = new AliasedQuerySourceNode();
        currentNode.setCqlNodeEquivalent(aliasedQuerySource);
        currentNode.setName(aliasedQuerySource.getAlias());
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitAliasedQuerySource(aliasedQuerySource, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitReturnClause(ReturnClause returnClause, BulkElmToSparkSQLConverterState context) {
        var currentNode = new ReturnClauseNode();
        currentNode.setCqlNodeEquivalent(returnClause);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitReturnClause(returnClause, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitWhereClause(Expression where, BulkElmToSparkSQLConverterState context) {
        var currentNode = new WhereNode();
        OutputNode<? extends Trackable> result = super.visitWhereClause(where, context);
        currentNode.addChild(result);
        return currentNode;
    }

    @Override
    public OutputNode<? extends Trackable> visitDateTime(DateTime dateTime, BulkElmToSparkSQLConverterState context) {
        var currentNode = new DateTimeNode();
        currentNode.setCqlNodeEquivalent(dateTime);
        if (dateTime.getYear() != null) {
            currentNode.setYear(visitExpression(dateTime.getYear(), context));
        }
        if (dateTime.getMonth() != null) {
            currentNode.setMonth(visitExpression(dateTime.getMonth(), context));
        }
        if (dateTime.getDay() != null) {
            currentNode.setDay(visitExpression(dateTime.getDay(), context));
        }
        if (dateTime.getHour() != null) {
            currentNode.setHour(visitExpression(dateTime.getHour(), context));
        }
        if (dateTime.getMinute() != null) {
            currentNode.setMinute(visitExpression(dateTime.getMinute(), context));
        }
        if (dateTime.getSecond() != null) {
            currentNode.setSecond(visitExpression(dateTime.getSecond(), context));
        }
        if (dateTime.getMillisecond() != null) {
            currentNode.setMillisecond(visitExpression(dateTime.getMillisecond(), context));
        }
        if (dateTime.getTimezoneOffset() != null) {
            currentNode.setTimezoneOffset(visitExpression(dateTime.getTimezoneOffset(), context));
        }
        return currentNode;
    }

    @Override
    public OutputNode<? extends Trackable> visitAfter(After after, BulkElmToSparkSQLConverterState context) {
        var currentNode = new AfterNode();
        currentNode.setCqlNodeEquivalent(after);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitAfter(after, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitEnd(End end, BulkElmToSparkSQLConverterState context) {
        var currentNode = new EndNode();
        currentNode.setCqlNodeEquivalent(end);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitEnd(end, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitAdd(Add add, BulkElmToSparkSQLConverterState context) {
        var currentNode = new BinaryOperatorNode<Add>("+");
        currentNode.setCqlNodeEquivalent(add);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitAdd(add, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitSubtract(Subtract subtract, BulkElmToSparkSQLConverterState context) {
        var currentNode = new BinaryOperatorNode<Subtract>("-");
        currentNode.setCqlNodeEquivalent(subtract);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitSubtract(subtract, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitDivide(Divide divide, BulkElmToSparkSQLConverterState context) {
        var currentNode = new BinaryOperatorNode<Divide>("/");
        currentNode.setCqlNodeEquivalent(divide);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitDivide(divide, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitMultiply(Multiply multiply, BulkElmToSparkSQLConverterState context) {
        var currentNode = new BinaryOperatorNode<Multiply>("*");
        currentNode.setCqlNodeEquivalent(multiply);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitMultiply(multiply, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitConcatenate(Concatenate concatenate, BulkElmToSparkSQLConverterState context) {
        var currentNode = new ConcatenateNode();
        currentNode.setCqlNodeEquivalent(concatenate);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitConcatenate(concatenate, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitNegate(Negate negate, BulkElmToSparkSQLConverterState context) {
        var currentNode = new NegateNode();
        currentNode.setCqlNodeEquivalent(negate);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitNegate(negate, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitToDecimal(ToDecimal toDecimal, BulkElmToSparkSQLConverterState context) {
        var currentNode = new ToDecimalNode();
        currentNode.setCqlNodeEquivalent(toDecimal);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitToDecimal(toDecimal, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode<? extends Trackable> visitEqual(Equal equal, BulkElmToSparkSQLConverterState context) {
        var currentNode = new BinaryOperatorNode<Equal>("=");
        currentNode.setCqlNodeEquivalent(equal);
        context.getStack().push(currentNode);
        OutputNode<? extends Trackable> result = super.visitEqual(equal, context);
        context.getStack().pop();
        return result;
    }
}
