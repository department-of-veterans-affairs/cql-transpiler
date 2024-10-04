package gov.va.transpiler.jinja.converter;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.*;

import gov.va.transpiler.jinja.node.*;
import gov.va.transpiler.jinja.node.trackable.*;
import gov.va.transpiler.jinja.node.trackable.element.*;
import gov.va.transpiler.jinja.node.trackable.element.expression.*;
import gov.va.transpiler.jinja.node.trackable.element.expression.aggregateexpression.*;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.*;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.binaryexpression.*;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.naryexpression.*;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression.*;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.*;
import gov.va.transpiler.jinja.node.trackable.element.typespecifier.*;
import gov.va.transpiler.jinja.state.State;

/**
 * Converts the CQL AST into a tree structure of {@link TranspilerNode}s able to compile and print an intermediate AST.
 */
public class Converter extends ElmBaseLibraryVisitor<TranspilerNode, State> {

    private State state = null;

    /**
     * Entry point.
     * 
     * @param library CQL AST node to convert.
     * @param state Used to keep track of state variables.
     * @return Root node for the intermediate AST.
     */
    public TranspilerNode convert(Library library, State state) {
        this.state = state;
        return visitElement(library, state);
    }

    @Override
    protected TranspilerNode defaultResult(Trackable elm, State state) {
        TranspilerNode current = null;
        if (state.getCurrentNode() instanceof CQLEquivalent && elm == state.getCurrentCQLNode()) {
            // If we support the specific type of Trackable provided, the last TranspilerNode created will be the current node.
            current = state.getCurrentNode();
        } else {
            throw new RuntimeException("hit unsupported element [" + elm.getClass() + "]");
        }
        return current;
    }

    @Override
    protected TranspilerNode aggregateResult(TranspilerNode aggregate, TranspilerNode nextResult) {
        aggregate.addChild(nextResult);
        state.setCurrentNode(aggregate);
        state.setCurrentCQLNode(((CQLEquivalent<?>) aggregate).getCqlEquivalent());
        return aggregate;
    }

    @Override
    public TranspilerNode visitAccessModifier(AccessModifier accessModifier, State state) {
        state.setCurrentCQLNode(null);
        return new DisabledNode(state, null);
    }

    @Override
    public TranspilerNode visitAdd(Add element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<Add>(state, element);
        return super.visitAdd(element, state);
    }

    @Override
    public TranspilerNode visitAs(As element, State state) {
        state.setCurrentCQLNode(element);
        new AsNode(state, element);
        return super.visitAs(element, state);
    }

    @Override
    public TranspilerNode visitAfter(After element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<After>(state, element);
        return super.visitAfter(element, state);
    }

    @Override
    public TranspilerNode visitAliasedQuerySource(AliasedQuerySource element, State state) {
        if (element instanceof RelationshipClause) {
            return super.visitRelationshipClause((RelationshipClause) element, state);
        }
        state.setCurrentCQLNode(element);
        new AliasedQuerySourceNode<AliasedQuerySource>(state, element);
        return super.visitAliasedQuerySource(element, state);
    }

    @Override
    public TranspilerNode visitAliasRef(AliasRef element, State state) {
        state.setCurrentCQLNode(element);
        new AliasRefNode(state, element);
        return super.visitAliasRef(element, state);
    }

    @Override
    public TranspilerNode visitAnd(And element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<And>(state, element);
        return super.visitAnd(element, state);
    }

    @Override
    public TranspilerNode visitBefore(Before element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<Before>(state, element);
        return super.visitBefore(element, state);
    }

    @Override
    public TranspilerNode visitChildren(BinaryExpression element, State state) {
        if (state.getCurrentCQLNode() != element) {
            throw new IllegalArgumentException("We don't support the binary expression [" + element + "] yet");
        }
        return super.visitChildren(element, state);
    }

    @Override
    public TranspilerNode visitByColumn(ByColumn element, State state) {
        state.setCurrentCQLNode(element);
        new ByColumnNode(state, element);
        return super.visitByColumn(element, state);
    }

    @Override
    public TranspilerNode visitByDirection(ByDirection element, State state) {
        state.setCurrentCQLNode(element);
        new ByDirectionNode(state, element);
        return super.visitByDirection(element, state);
    }

    @Override
    public TranspilerNode visitByExpression(ByExpression element, State state) {
        state.setCurrentCQLNode(element);
        new ByExpressionNode(state, element);
        return super.visitByExpression(element, state);
    }

    @Override
    public TranspilerNode visitCalculateAgeAt(CalculateAgeAt element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<CalculateAgeAt>(state, element);
        return super.visitCalculateAgeAt(element, state);
    }

    @Override
    public TranspilerNode visitChoiceTypeSpecifier(ChoiceTypeSpecifier element, State state) {
        state.setCurrentCQLNode(element);
        new ChoiceTypeSpecifierNode(state, element);
        return super.visitChoiceTypeSpecifier(element, state);
    }

    @Override
    public TranspilerNode visitCoalesce(Coalesce element, State state) {
        state.setCurrentCQLNode(element);
        new NaryExpressionNode<Coalesce>(state, element);
        return super.visitCoalesce(element, state);
    }

    @Override
    public TranspilerNode visitConcatenate(Concatenate element, State state) {
        state.setCurrentCQLNode(element);
        new NaryExpressionNode<Concatenate>(state, element);
        return super.visitConcatenate(element, state);
    }

    @Override
    public TranspilerNode visitContextDef(ContextDef element, State state) {
        state.setCurrentCQLNode(element);
        new ContextDefNode(state, element);
        return super.visitContextDef(element, state);
    }

    @Override
    public TranspilerNode visitCount(Count element, State state) {
        state.setCurrentCQLNode(element);
        new CountNode(state, element);
        return super.visitCount(element, state);
    }

    @Override
    public TranspilerNode visitDateTime(DateTime element, State state) {
        state.setCurrentCQLNode(element);
        new DateTimeNode(state, element);
        return super.visitDateTime(element, state);
    }

    @Override
    public TranspilerNode visitDateTimeComponentFrom(DateTimeComponentFrom element, State state) {
        state.setCurrentCQLNode(element);
        new DateTimeComponentFromNode(state, element);
        return super.visitDateTimeComponentFrom(element, state);
    }

    @Override
    public TranspilerNode visitDateFrom(DateFrom element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<DateFrom>(state, element);
        return super.visitDateFrom(element, state);
    }


    @Override
    public TranspilerNode visitDifferenceBetween(DifferenceBetween element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<DifferenceBetween>(state, element);
        return super.visitDifferenceBetween(element, state);
    }

    @Override
    public TranspilerNode visitDivide(Divide element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<Divide>(state, element);
        return super.visitDivide(element, state);
    }

    @Override
    public TranspilerNode visitEnd(End element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<End>(state, element);
        return super.visitEnd(element, state);
    }

    @Override
    public TranspilerNode visitEqual(Equal element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<Equal>(state, element);
        return super.visitEqual(element, state);
    }

    @Override
    public TranspilerNode visitExists(Exists element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<Exists>(state, element);
        return super.visitExists(element, state);
    }

    @Override
    public TranspilerNode visitExpressionDef(ExpressionDef element, State state) {
        if (element instanceof FunctionDef) {
            return super.visitExpressionDef((FunctionDef) element, state);
        }
        state.setCurrentCQLNode(element);
        new ExpressionDefNode<ExpressionDef>(state, element);
        return super.visitExpressionDef(element, state);
    }

    @Override
    public TranspilerNode visitExpressionRef(ExpressionRef element, State state) {
        state.setCurrentCQLNode(element);
        new ExpressionRefNode<ExpressionRef>(state, element);
        return super.visitExpressionRef(element, state);
    }

    @Override
    public TranspilerNode visitFirst(First element, State state) {
        state.setCurrentCQLNode(element);
        new FirstNode(state, element);
        return super.visitFirst(element, state);
    }

    @Override
    public TranspilerNode visitFlatten(Flatten element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<Flatten>(state, element);
        return super.visitFlatten(element, state);
    }

    @Override
    public TranspilerNode visitFunctionDef(FunctionDef element, State state) {
        state.setCurrentCQLNode(element);
        new FunctionDefNode(state, element);
        return super.visitFunctionDef(element, state);
    }

    @Override
    public TranspilerNode visitFunctionRef(FunctionRef element, State state) {
        state.setCurrentCQLNode(element);
        new FunctionRefNode(state, element);
        return super.visitFunctionRef(element, state);
    }

    @Override
    public TranspilerNode visitGreater(Greater element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<Greater>(state, element);
        return super.visitGreater(element, state);
    }

    @Override
    public TranspilerNode visitGreaterOrEqual(GreaterOrEqual element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<GreaterOrEqual>(state, element);
        return super.visitGreaterOrEqual(element, state);
    }

    @Override
    public TranspilerNode visitIdentifierRef(IdentifierRef element, State state) {
        state.setCurrentCQLNode(element);
        new IdentifierRefNode(state, element);
        return super.visitIdentifierRef(element, state);
    }

    @Override
    public TranspilerNode visitIf(If element, State state) {
        state.setCurrentCQLNode(element);
        new IfNode(state, element);
        return super.visitIf(element, state);
    }

    @Override
    public TranspilerNode visitIn(In element, State state) {
        state.setCurrentCQLNode(element);
        new InNode(state, element);
        return super.visitIn(element, state);
    }

    @Override
    public TranspilerNode visitIncludedIn(IncludedIn element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<IncludedIn>(state, element);
        return super.visitIncludedIn(element, state);
    }

    @Override
    public TranspilerNode visitIncludes(Includes element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<Includes>(state, element);
        return super.visitIncludes(element, state);
    }

    @Override
    public TranspilerNode visitInValueSet(InValueSet element, State state) {
        state.setCurrentCQLNode(element);
        new InValueSetNode(state, element);
        return super.visitInValueSet(element, state);
    }

    @Override
    public TranspilerNode visitIncludeDef(IncludeDef element, State state) {
        state.setCurrentCQLNode(element);
        new IncludeDefNode(state, element);
        return super.visitIncludeDef(element, state);
    }

    @Override
    public TranspilerNode visitInterval(Interval element, State state) {
        state.setCurrentCQLNode(element);
        new IntervalNode(state, element);
        return super.visitInterval(element, state);
    }

    @Override
    public TranspilerNode visitIntervalTypeSpecifier(IntervalTypeSpecifier element, State state) {
        state.setCurrentCQLNode(element);
        new IntervalTypeSpecifierNode(state, element);
        return super.visitIntervalTypeSpecifier(element, state);
    }

    @Override
    public TranspilerNode visitIsNull(IsNull element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<IsNull>(state, element);
        return super.visitIsNull(element, state);
    }

    @Override
    public TranspilerNode visitLast(Last element, State state) {
        state.setCurrentCQLNode(element);
        new LastNode(state, element);
        return super.visitLast(element, state);
    }

    @Override
    public TranspilerNode visitLess(Less element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<Less>(state, element);
        return super.visitLess(element, state);
    }

    @Override
    public TranspilerNode visitLessOrEqual(LessOrEqual element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<LessOrEqual>(state, element);
        return super.visitLessOrEqual(element, state);
    }

    @Override
    public TranspilerNode visitLetClause(LetClause element, State state) {
        state.setCurrentCQLNode(element);
        new LetClauseNode(state, element);
        // super.visitLetClause has a nonstandard format, so do all the expected steps here
        var result = defaultResult(element, state);
        if (element.getExpression() != null) {
            var childResult = visitElement(element.getExpression(), state);
            result = aggregateResult(result, childResult);
        }
        return result;
    }

    @Override
    public TranspilerNode visitLibrary(Library element, State state) {
        state.setCurrentCQLNode(element);
        new LibraryNode(state, element);
        return super.visitLibrary(element, state);
    }

    @Override
    public TranspilerNode visitList(List element, State state) {
        state.setCurrentCQLNode(element);
        new ListNode(state, element);
        return super.visitList(element, state);
    }

    @Override
    public TranspilerNode visitListTypeSpecifier(ListTypeSpecifier element, State state) {
        state.setCurrentCQLNode(element);
        new ListTypeSpecifierNode(state, element);
        return super.visitListTypeSpecifier(element, state);
    }

    @Override
    public TranspilerNode visitLiteral(Literal element, State state) {
        state.setCurrentCQLNode(element);
        new LiteralNode(state, element);
        return super.visitLiteral(element, state);
    }

    @Override
    public TranspilerNode visitMaxValue(MaxValue element, State state) {
        state.setCurrentCQLNode(element);
        new MaxValueNode(state, element);
        return super.visitMaxValue(element, state);
    }

    @Override
    public TranspilerNode visitMinValue(MinValue element, State state) {
        state.setCurrentCQLNode(element);
        new MinValueNode(state, element);
        return super.visitMinValue(element, state);
    }

    @Override
    public TranspilerNode visitMultiply(Multiply element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<Multiply>(state, element);
        return super.visitMultiply(element, state);
    }

    @Override
    public TranspilerNode visitOperandDef(OperandDef element, State state) {
        state.setCurrentCQLNode(element);
        new OperandDefNode(state, element);
        return super.visitOperandDef(element, state);
    }

    @Override
    public TranspilerNode visitOperandRef(OperandRef element, State state) {
        state.setCurrentCQLNode(element);
        new OperandRefNode(state, element);
        return super.visitOperandRef(element, state);
    }

    @Override
    public TranspilerNode visitOr(Or element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<Or>(state, element);
        return super.visitOr(element, state);
    }

    @Override
    public TranspilerNode visitParameterDef(ParameterDef element, State state) {
        state.setCurrentCQLNode(element);
        new ParameterDefNode(state, element);
        return super.visitParameterDef(element, state);
    }

    @Override
    public TranspilerNode visitParameterRef(ParameterRef element, State state) {
        state.setCurrentCQLNode(element);
        new ParameterRefNode(state, element);
        return super.visitParameterRef(element, state);
    }

    @Override
    public TranspilerNode visitProperty(Property element, State state) {
        if (element instanceof Search) {
            return super.visitSearch((Search) element, state);
        }
        state.setCurrentCQLNode(element);
        new PropertyNode(state, element);
        return super.visitProperty(element, state);
    }

    @Override
    public TranspilerNode visitQuantity(Quantity element, State state) {
        state.setCurrentCQLNode(element);
        new QuantityNode(state, element);
        return super.visitQuantity(element, state);
    }

    @Override
    public TranspilerNode visitQueryLetRef(QueryLetRef element, State state) {
        state.setCurrentCQLNode(element);
        new QueryLetRefNode(state, element);
        return super.visitQueryLetRef(element, state);
    }

    @Override
    public TranspilerNode visitQuery(Query element, State state) {
        state.setCurrentCQLNode(element);
        new QueryNode(state, element);
        return super.visitQuery(element, state);
    }

    @Override
    public TranspilerNode visitRetrieve(Retrieve element, State state) {
        state.setCurrentCQLNode(element);
        new RetrieveNode(state, element);
        return super.visitRetrieve(element, state);
    }

    @Override
    public TranspilerNode visitReturnClause(ReturnClause element, State state) {
        state.setCurrentCQLNode(element);
        new ReturnClauseNode(state, element);
        return super.visitReturnClause(element, state);
    }

    @Override
    public TranspilerNode visitSingletonFrom(SingletonFrom element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<SingletonFrom>(state, element);
        return super.visitSingletonFrom(element, state);
    }

    @Override
    public TranspilerNode visitStart(Start element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<Start>(state, element);
        return super.visitStart(element, state);
    }

    @Override
    public TranspilerNode visitSortByItem(SortByItem element, State state) {
        if (element instanceof ByColumn) {
            return visitByColumn((ByColumn) element, state);
        } else if (element instanceof ByDirection) {
            return visitByDirection((ByDirection) element, state);
        } else if (element instanceof ByExpression) {
            return visitByExpression((ByExpression) element, state);
        }
        state.setCurrentCQLNode(element);
        new SortByItemNode<SortByItem>(state, element);
        return super.visitSortByItem(element, state);
    }

    @Override
    public TranspilerNode visitSortClause(SortClause element, State state) {
        state.setCurrentCQLNode(element);
        new SortClauseNode(state, element);
        return super.visitSortClause(element, state);
    }

    @Override
    public TranspilerNode visitSubtract(Subtract element, State state) {
        state.setCurrentCQLNode(element);
        new BinaryExpressionNode<Subtract>(state, element);
        return super.visitSubtract(element, state);
    }

    @Override
    public TranspilerNode visitTimezoneOffsetFrom(TimezoneOffsetFrom element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<TimezoneOffsetFrom>(state, element);
        return super.visitTimezoneOffsetFrom(element, state);
    }

    @Override
    public TranspilerNode visitToDecimal(ToDecimal element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<ToDecimal>(state, element);
        return super.visitToDecimal(element, state);
    }

    @Override
    public TranspilerNode visitToDate(ToDate element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<ToDate>(state, element);
        return super.visitToDate(element, state);
    }

    @Override
    public TranspilerNode visitTuple(Tuple element, State state) {
        state.setCurrentCQLNode(element);
        new TupleNode(state, element);
        return super.visitTuple(element, state);
    }

    @Override
    public TranspilerNode visitTupleElement(TupleElement element, State state) {
        state.setCurrentCQLNode(element);
        new TupleElementNode(state, element);
        return super.visitTupleElement(element, state);
    }

    @Override
    public TranspilerNode visitNamedTypeSpecifier(NamedTypeSpecifier element, State state) {
        state.setCurrentCQLNode(element);
        new NamedTypeSpecifierNode(state, element);
        return super.visitNamedTypeSpecifier(element, state);
    }

    @Override
    public TranspilerNode visitNegate(Negate element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<Negate>(state, element);
        return super.visitNegate(element, state);
    }

    @Override
    public TranspilerNode visitNot(Not element, State state) {
        state.setCurrentCQLNode(element);
        new UnaryExpressionNode<Not>(state, element);
        return super.visitNot(element, state);
    }

    @Override
    public TranspilerNode visitNull(Null element, State state) {
        state.setCurrentCQLNode(element);
        new NullNode(state, element);
        return super.visitNull(element, state);
    }

    @Override
    public TranspilerNode visitUnion(Union element, State state) {
        state.setCurrentCQLNode(element);
        new UnionNode(state, element);
        return super.visitUnion(element, state);
    }

    @Override
    public TranspilerNode visitUsingDef(UsingDef element, State state) {
        state.setCurrentCQLNode(element);
        new UsingDefNode(state, element);
        return super.visitUsingDef(element, state);
    }

    @Override
    public TranspilerNode visitValueSetDef(ValueSetDef element, State state) {
        state.setCurrentCQLNode(element);
        new ValueSetDefNode(state, element);
        return super.visitValueSetDef(element, state);
    }

    @Override
    public TranspilerNode visitValueSetRef(ValueSetRef element, State state) {
        state.setCurrentCQLNode(element);
        new ValueSetRefNode(state, element);
        return super.visitValueSetRef(element, state);
    }

    @Override
    public TranspilerNode visitWith(With element, State state) {
        state.setCurrentCQLNode(element);
        new WithNode(state, element);
        return super.visitWith(element, state);
    }
}
