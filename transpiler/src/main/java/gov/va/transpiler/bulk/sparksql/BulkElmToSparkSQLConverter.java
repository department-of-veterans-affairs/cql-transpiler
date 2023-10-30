package gov.va.transpiler.bulk.sparksql;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.*;

import gov.va.transpiler.ElmConverter;
import gov.va.transpiler.bulk.sparksql.node.*;
import gov.va.transpiler.bulk.sparksql.utilities.CQLNameToSparkSQLName;
import gov.va.transpiler.bulk.sparksql.utilities.CQLTypeToSparkSQLType;
import gov.va.transpiler.node.OutputNode;

public class BulkElmToSparkSQLConverter extends ElmConverter<OutputNode, BulkElmToSparkSQLConverterState> {

    private final CQLTypeToSparkSQLType cqlTypeToSparkSQLType;
    private final CQLNameToSparkSQLName cqlNameToSparkSQLName;

    public BulkElmToSparkSQLConverter(CQLTypeToSparkSQLType cqlTypeToSparkSQLType, CQLNameToSparkSQLName cqlNameToSparkSQLName) {
        this.cqlTypeToSparkSQLType = cqlTypeToSparkSQLType;
        this.cqlNameToSparkSQLName = cqlNameToSparkSQLName;
    }

    @Override
    public OutputNode convert(Library library, BulkElmToSparkSQLConverterState state) {
        return visitElement(library, state);
    }

    @Override
    protected OutputNode defaultResult(Trackable elm, BulkElmToSparkSQLConverterState context) {
        OutputNode stackLocation;
        if (!context.getStack().empty() && elm == (stackLocation = context.getStack().peek()).getCqlNodeEquivalent()) {
            return stackLocation;
        }
        stackLocation = new DefaultOutputNode();
        stackLocation.setName(defaultNodeName(elm));
        stackLocation.setCqlNodeEquivalent(elm);
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

    protected String defaultNodeName(Trackable elm) {
        return elm == null ? "NullElement" : elm.getClass().getSimpleName();
    }

    @Override
    public OutputNode visitLibrary(Library library, BulkElmToSparkSQLConverterState context) {
        var currentNode = new LibraryNode();
        currentNode.setName(currentNode.getFileNameFromLibrary(library));
        currentNode.setCqlNodeEquivalent(library);
        context.getStack().push(currentNode);
        OutputNode result = super.visitLibrary(library, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitUsingDef(UsingDef usingDef, BulkElmToSparkSQLConverterState context) {
        var currentNode = new UsingDefNode();
        currentNode.setCqlNodeEquivalent(usingDef);
        return currentNode;
    }

    @Override
    public OutputNode visitLiteral(Literal literal, BulkElmToSparkSQLConverterState context) {
        var currentNode = new LiteralNode(cqlTypeToSparkSQLType);
        currentNode.setCqlNodeEquivalent(literal);
        currentNode.setName(literal.getValue());
        currentNode.setResultType(literal.getResultType().toString());
        currentNode.setCqlNodeEquivalent(literal);
        return currentNode;
    }

    @Override
    public OutputNode visitAccessModifier(AccessModifier accessModifier, BulkElmToSparkSQLConverterState context) {
        var currentNode = new AccessModifierNode();
        currentNode.setCqlNodeEquivalent(accessModifier);
        return currentNode;
    }

    @Override
    public OutputNode visitExpressionDef(ExpressionDef expressionDef, BulkElmToSparkSQLConverterState context) {
        var currentNode = new ExpressionDefNode(cqlNameToSparkSQLName);
        currentNode.setName(expressionDef.getName());
        currentNode.setCqlNodeEquivalent(expressionDef);
        context.getStack().push(currentNode);
        OutputNode result = super.visitExpressionDef(expressionDef, context);
        context.getStack().pop();
        context.getDefinedExpressions().put(currentNode.getName(), currentNode);
        return result;
    }

    @Override
    public OutputNode visitExpressionRef(ExpressionRef expressionRef, BulkElmToSparkSQLConverterState context) {
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
    public OutputNode visitList(List list, BulkElmToSparkSQLConverterState context) {
        var currentNode = new ListNode();
        currentNode.setCqlNodeEquivalent(list);
        context.getStack().push(currentNode);
        OutputNode result = super.visitList(list, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitTupleElement(TupleElement tupleElement, BulkElmToSparkSQLConverterState context) {
        var currentNode = new TupleElementNode();
        currentNode.setCqlNodeEquivalent(tupleElement);
        currentNode.setName(tupleElement.getName());
        context.getStack().push(currentNode);
        OutputNode result = super.visitTupleElement(tupleElement, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitTuple(Tuple tuple, BulkElmToSparkSQLConverterState context) {
        var currentNode = new TupleNode();
        currentNode.setCqlNodeEquivalent(tuple);
        context.getStack().push(currentNode);
        OutputNode result = super.visitTuple(tuple, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitRetrieve(Retrieve retrieve, BulkElmToSparkSQLConverterState context) {
        var currentNode = new RetrieveNode();
        // TODO: We'll worry about retrieve.getDataType().getNamespaceURI() later
        currentNode.setName(retrieve.getDataType().getLocalPart());
        currentNode.setCqlNodeEquivalent(retrieve);
        return currentNode;
    }

    @Override
    public OutputNode visitProperty(Property property, BulkElmToSparkSQLConverterState context) {
        var currentNode = new PropertyNode();
        currentNode.setCqlNodeEquivalent(property);
        currentNode.setResultType(property.getResultType());
        currentNode.setName(property.getPath());
        currentNode.setScope(property.getScope());
        context.getStack().push(currentNode);
        OutputNode result = super.visitProperty(property, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitQuery(Query query, BulkElmToSparkSQLConverterState context) {
        var currentNode = new QueryNode();
        currentNode.setCqlNodeEquivalent(query);
        context.getStack().push(currentNode);
        OutputNode result = super.visitQuery(query, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitAliasedQuerySource(AliasedQuerySource aliasedQuerySource, BulkElmToSparkSQLConverterState context) {
        var currentNode = new AliasedQuerySourceNode();
        currentNode.setCqlNodeEquivalent(aliasedQuerySource);
        currentNode.setName(aliasedQuerySource.getAlias());
        context.getStack().push(currentNode);
        OutputNode result = super.visitAliasedQuerySource(aliasedQuerySource, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitReturnClause(ReturnClause returnClause, BulkElmToSparkSQLConverterState context) {
        var currentNode = new ReturnClauseNode();
        currentNode.setCqlNodeEquivalent(returnClause);
        context.getStack().push(currentNode);
        OutputNode result = super.visitReturnClause(returnClause, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitWhereClause(Expression where, BulkElmToSparkSQLConverterState context) {
        var currentNode = new WhereNode();
        OutputNode result = super.visitWhereClause(where, context);
        currentNode.addChild(result);
        return currentNode;
    }

    @Override
    public OutputNode visitDateTime(DateTime dateTime, BulkElmToSparkSQLConverterState context) {
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
    public OutputNode visitAfter(After after, BulkElmToSparkSQLConverterState context) {
        var currentNode = new AfterNode();
        currentNode.setCqlNodeEquivalent(after);
        context.getStack().push(currentNode);
        OutputNode result = super.visitAfter(after, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public OutputNode visitEnd(End end, BulkElmToSparkSQLConverterState context) {
        var currentNode = new EndNode();
        currentNode.setCqlNodeEquivalent(end);
        context.getStack().push(currentNode);
        OutputNode result = super.visitEnd(end, context);
        context.getStack().pop();
        return result;
    }
}
