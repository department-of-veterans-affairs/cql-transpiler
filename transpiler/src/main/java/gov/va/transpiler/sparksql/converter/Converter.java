package gov.va.transpiler.sparksql.converter;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.AccessModifier;
import org.hl7.elm.r1.Add;
import org.hl7.elm.r1.After;
import org.hl7.elm.r1.AliasedQuerySource;
import org.hl7.elm.r1.Concatenate;
import org.hl7.elm.r1.ContextDef;
import org.hl7.elm.r1.Count;
import org.hl7.elm.r1.DateTime;
import org.hl7.elm.r1.Divide;
import org.hl7.elm.r1.End;
import org.hl7.elm.r1.Equal;
import org.hl7.elm.r1.Expression;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.ExpressionRef;
import org.hl7.elm.r1.FunctionRef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.List;
import org.hl7.elm.r1.Literal;
import org.hl7.elm.r1.Multiply;
import org.hl7.elm.r1.Negate;
import org.hl7.elm.r1.Property;
import org.hl7.elm.r1.Query;
import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ReturnClause;
import org.hl7.elm.r1.SingletonFrom;
import org.hl7.elm.r1.Subtract;
import org.hl7.elm.r1.ToDecimal;
import org.hl7.elm.r1.Tuple;
import org.hl7.elm.r1.TupleElement;
import org.hl7.elm.r1.UsingDef;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.DefaultOutputNode;
import gov.va.transpiler.sparksql.node.ary.LibraryNode;
import gov.va.transpiler.sparksql.node.ary.ListNode;
import gov.va.transpiler.sparksql.node.ary.QueryNode;
import gov.va.transpiler.sparksql.node.ary.RetrieveNode;
import gov.va.transpiler.sparksql.node.ary.TupleNode;
import gov.va.transpiler.sparksql.node.ary.WhereNode;
import gov.va.transpiler.sparksql.node.binary.BinaryOperatorNode;
import gov.va.transpiler.sparksql.node.binary.ConcatenateNode;
import gov.va.transpiler.sparksql.node.leaf.AccessModifierNode;
import gov.va.transpiler.sparksql.node.leaf.ContextDefNode;
import gov.va.transpiler.sparksql.node.leaf.DateTimeNode;
import gov.va.transpiler.sparksql.node.leaf.ExpressionRefNode;
import gov.va.transpiler.sparksql.node.leaf.LiteralNode;
import gov.va.transpiler.sparksql.node.leaf.UsingDefNode;
import gov.va.transpiler.sparksql.node.unary.AliasedQuerySourceNode;
import gov.va.transpiler.sparksql.node.unary.CountNode;
import gov.va.transpiler.sparksql.node.unary.EndNode;
import gov.va.transpiler.sparksql.node.unary.ExpressionDefNode;
import gov.va.transpiler.sparksql.node.unary.NegateNode;
import gov.va.transpiler.sparksql.node.unary.PropertyNode;
import gov.va.transpiler.sparksql.node.unary.ReturnClauseNode;
import gov.va.transpiler.sparksql.node.unary.SingletonFromNode;
import gov.va.transpiler.sparksql.node.unary.ToDecimalNode;
import gov.va.transpiler.sparksql.node.unary.TupleElementNode;
import gov.va.transpiler.sparksql.utilities.CQLNameToSparkSQLName;
import gov.va.transpiler.sparksql.utilities.CQLTypeToSparkSQLType;

public class Converter extends ElmBaseLibraryVisitor<AbstractCQLNode, State> {

    private final CQLTypeToSparkSQLType cqlTypeToSparkSQLType;
    private final CQLNameToSparkSQLName cqlNameToSparkSQLName;

    public Converter(CQLTypeToSparkSQLType cqlTypeToSparkSQLType, CQLNameToSparkSQLName cqlNameToSparkSQLName) {
        this.cqlTypeToSparkSQLType = cqlTypeToSparkSQLType;
        this.cqlNameToSparkSQLName = cqlNameToSparkSQLName;
    }

    public AbstractCQLNode convert(Library library, State state) {
        return visitElement(library, state);
    }

    @Override
    protected AbstractCQLNode defaultResult(Trackable elm, State context) {
        if (!context.getStack().empty() && elm == context.getStack().peek().getCqlNodeEquivalent()) {
            return context.getStack().peek();
        }
        AbstractCQLNode placeholder = new DefaultOutputNode();
        placeholder.setName(defaultNodeName(elm));
        placeholder.setCqlNodeEquivalent(elm);
        context.getStack().push(placeholder);
        return placeholder;
    }

    @Override
    protected AbstractCQLNode aggregateResult(AbstractCQLNode aggregate, AbstractCQLNode nextResult) {
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
    public AbstractCQLNode visitLibrary(Library library, State context) {
        var currentNode = new LibraryNode();
        currentNode.setName(currentNode.getFileNameFromLibrary(library));
        currentNode.setCqlNodeEquivalent(library);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitLibrary(library, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitUsingDef(UsingDef usingDef, State context) {
        var currentNode = new UsingDefNode();
        currentNode.setCqlNodeEquivalent(usingDef);
        return currentNode;
    }

    @Override
    public AbstractCQLNode visitLiteral(Literal literal, State context) {
        var currentNode = new LiteralNode(cqlTypeToSparkSQLType);
        currentNode.setCqlNodeEquivalent(literal);
        currentNode.setName(literal.getValue());
        currentNode.setResultType(literal.getResultType().toString());
        currentNode.setCqlNodeEquivalent(literal);
        return currentNode;
    }

    @Override
    public AbstractCQLNode visitAccessModifier(AccessModifier accessModifier, State context) {
        var currentNode = new AccessModifierNode();
        return currentNode;
    }

    @Override
    public AbstractCQLNode visitExpressionDef(ExpressionDef expressionDef, State context) {
        var currentNode = new ExpressionDefNode(cqlNameToSparkSQLName);
        context.setCqlContext(expressionDef.getContext());
        currentNode.setName(expressionDef.getName());
        currentNode.setCqlNodeEquivalent(expressionDef);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitExpressionDef(expressionDef, context);
        context.getStack().pop();
        context.getDefinedExpressions().put(currentNode.getName(), currentNode);
        context.setCqlContext(null);
        return result;
    }

    @Override
    public AbstractCQLNode visitExpressionRef(ExpressionRef expressionRef, State context) {
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
    public AbstractCQLNode visitList(List list, State context) {
        var currentNode = new ListNode();
        currentNode.setCqlNodeEquivalent(list);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitList(list, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitTupleElement(TupleElement tupleElement, State context) {
        var currentNode = new TupleElementNode();
        currentNode.setCqlNodeEquivalent(tupleElement);
        currentNode.setName(tupleElement.getName());
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitTupleElement(tupleElement, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitTuple(Tuple tuple, State context) {
        var currentNode = new TupleNode();
        currentNode.setCqlNodeEquivalent(tuple);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitTuple(tuple, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitRetrieve(Retrieve retrieve, State context) {
        var currentNode = new RetrieveNode();
        // TODO: We'll worry about retrieve.getDataType().getNamespaceURI() later
        currentNode.setName(retrieve.getDataType().getLocalPart());
        currentNode.setCqlNodeEquivalent(retrieve);
        currentNode.setCqlContext(context.getCqlContext());
        return currentNode;
    }

    @Override
    public AbstractCQLNode visitProperty(Property property, State context) {
        var currentNode = new PropertyNode();
        currentNode.setCqlNodeEquivalent(property);
        currentNode.setName(property.getPath());
        currentNode.setScope(property.getScope());
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitProperty(property, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitQuery(Query query, State context) {
        var currentNode = new QueryNode();
        currentNode.setCqlNodeEquivalent(query);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitQuery(query, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitAliasedQuerySource(AliasedQuerySource aliasedQuerySource, State context) {
        var currentNode = new AliasedQuerySourceNode();
        currentNode.setCqlNodeEquivalent(aliasedQuerySource);
        currentNode.setName(aliasedQuerySource.getAlias());
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitAliasedQuerySource(aliasedQuerySource, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitReturnClause(ReturnClause returnClause, State context) {
        var currentNode = new ReturnClauseNode();
        currentNode.setCqlNodeEquivalent(returnClause);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitReturnClause(returnClause, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitWhereClause(Expression where, State context) {
        var currentNode = new WhereNode();
        AbstractCQLNode result = super.visitWhereClause(where, context);
        currentNode.addChild(result);
        return currentNode;
    }

    @Override
    public AbstractCQLNode visitDateTime(DateTime dateTime, State context) {
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
    public AbstractCQLNode visitAfter(After after, State context) {
        var currentNode = new BinaryOperatorNode(">");
        currentNode.setCqlNodeEquivalent(after);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitAfter(after, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitEnd(End end, State context) {
        var currentNode = new EndNode();
        currentNode.setCqlNodeEquivalent(end);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitEnd(end, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitAdd(Add add, State context) {
        var currentNode = new BinaryOperatorNode("+");
        currentNode.setCqlNodeEquivalent(add);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitAdd(add, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitSubtract(Subtract subtract, State context) {
        var currentNode = new BinaryOperatorNode("-");
        currentNode.setCqlNodeEquivalent(subtract);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitSubtract(subtract, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitDivide(Divide divide, State context) {
        var currentNode = new BinaryOperatorNode("/");
        currentNode.setCqlNodeEquivalent(divide);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitDivide(divide, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitMultiply(Multiply multiply, State context) {
        var currentNode = new BinaryOperatorNode("*");
        currentNode.setCqlNodeEquivalent(multiply);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitMultiply(multiply, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitConcatenate(Concatenate concatenate, State context) {
        var currentNode = new ConcatenateNode();
        currentNode.setCqlNodeEquivalent(concatenate);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitConcatenate(concatenate, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitNegate(Negate negate, State context) {
        var currentNode = new NegateNode();
        currentNode.setCqlNodeEquivalent(negate);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitNegate(negate, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitToDecimal(ToDecimal toDecimal, State context) {
        var currentNode = new ToDecimalNode();
        currentNode.setCqlNodeEquivalent(toDecimal);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitToDecimal(toDecimal, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitEqual(Equal equal, State context) {
        var currentNode = new BinaryOperatorNode("=");
        currentNode.setCqlNodeEquivalent(equal);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitEqual(equal, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitContextDef(ContextDef contextDef, State context) {
        var currentNode = new ContextDefNode();
        currentNode.setCqlNodeEquivalent(contextDef);
        currentNode.setName(contextDef.getName());
        return currentNode;
    }

    @Override
    public AbstractCQLNode visitSingletonFrom(SingletonFrom singletonFrom, State context) {
        var currentNode = new SingletonFromNode();
        currentNode.setCqlNodeEquivalent(singletonFrom);
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitSingletonFrom(singletonFrom, context);
        context.getStack().pop();
        return result;
    }

    @Override
    public AbstractCQLNode visitCount(Count count, State context) {
        var currentNode = new CountNode();
        currentNode.setCqlNodeEquivalent(count);
        currentNode.setCqlContext(context.getCqlContext());
        context.getStack().push(currentNode);
        AbstractCQLNode result = super.visitCount(count, context);
        context.getStack().pop();
        return result;
    }
}
