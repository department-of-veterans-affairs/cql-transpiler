package gov.va.transpiler.jinja.converter;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.*;

import gov.va.transpiler.jinja.node.DefaultNode;
import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.TupleElementNode;
import gov.va.transpiler.jinja.node.trackable.element.AliasedQuerySourceNode;
import gov.va.transpiler.jinja.node.trackable.element.ByColumnNode;
import gov.va.transpiler.jinja.node.trackable.element.ByDirectionNode;
import gov.va.transpiler.jinja.node.trackable.element.ByExpressionNode;
import gov.va.transpiler.jinja.node.trackable.element.ContextDefNode;
import gov.va.transpiler.jinja.node.trackable.element.IncludeDefNode;
import gov.va.transpiler.jinja.node.trackable.element.LetClauseNode;
import gov.va.transpiler.jinja.node.trackable.element.LibraryNode;
import gov.va.transpiler.jinja.node.trackable.element.OperandDefNode;
import gov.va.transpiler.jinja.node.trackable.element.ParameterDefNode;
import gov.va.transpiler.jinja.node.trackable.element.ReturnClauseNode;
import gov.va.transpiler.jinja.node.trackable.element.SortByItemNode;
import gov.va.transpiler.jinja.node.trackable.element.SortClauseNode;
import gov.va.transpiler.jinja.node.trackable.element.UsingDefNode;
import gov.va.transpiler.jinja.node.trackable.element.ValueSetDefNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.DateTimeNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.ExpressionRefNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.FunctionRefNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.IdentifierRefNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.ListNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.LiteralNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.OperandRefNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.ParameterRefNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.PropertyNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.QueryLetRefNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.QueryNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.RetrieveNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.TupleNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.aggregateexpression.CountNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.InValueSetNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.binaryexpression.BinaryExpressionNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.naryexpression.NaryExpressionNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.naryexpression.UnionNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression.FlattenNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.ExpressionDefNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.FunctionDefNode;
import gov.va.transpiler.jinja.node.trackable.element.typespecifier.IntervalTypeSpecifierNode;
import gov.va.transpiler.jinja.node.trackable.element.typespecifier.ListTypeSpecifierNode;
import gov.va.transpiler.jinja.node.trackable.element.typespecifier.NamedTypeSpecifierNode;
import gov.va.transpiler.jinja.state.State;
public class Converter extends ElmBaseLibraryVisitor<TranspilerNode, State> {

    public TranspilerNode convert(Library library, State state) {
        return visitElement(library, state);
    }

    @Override
    protected TranspilerNode defaultResult(Trackable elm, State state) {
        var current = state.getCurrentNode();
        if (current == null) {
            if (elm == null) {
                current = new DisabledNode(state, null);
            } else {
                current = new DefaultNode(state, elm);
            }
        }
        state.setCurrentNode(null);
        return current;
    }

    @Override
    protected TranspilerNode aggregateResult(TranspilerNode aggregate, TranspilerNode nextResult) {
        aggregate.addChild(nextResult);
        nextResult.setParent(aggregate);
        return aggregate;
    }

    @Override
    public TranspilerNode visitElement(Element element, State state) {
        try {
            return super.visitElement(element, state);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TranspilerNode visitAdd(Add element, State state) {
        new BinaryExpressionNode<Add>(state, element);
        return super.visitAdd(element, state);
    }

    @Override
    public TranspilerNode visitAfter(After element, State state) {
        new BinaryExpressionNode<After>(state, element);
        return super.visitAfter(element, state);
    }

    @Override
    public TranspilerNode visitAliasedQuerySource(AliasedQuerySource element, State state) {
        new AliasedQuerySourceNode(state, element);
        return super.visitAliasedQuerySource(element, state);
    }

    @Override
    public TranspilerNode visitAnd(And element, State state) {
        new BinaryExpressionNode<And>(state, element);
        return super.visitAnd(element, state);
    }

    @Override
    public TranspilerNode visitBefore(Before element, State state) {
        new BinaryExpressionNode<Before>(state, element);
        return super.visitBefore(element, state);
    }

    @Override
    public TranspilerNode visitByColumn(ByColumn element, State state) {
        new ByColumnNode(state, element);
        return super.visitByColumn(element, state);
    }

    @Override
    public TranspilerNode visitByDirection(ByDirection element, State state) {
        new ByDirectionNode(state, element);
        return super.visitByDirection(element, state);
    }

    @Override
    public TranspilerNode visitByExpression(ByExpression element, State state) {
        new ByExpressionNode(state, element);
        return super.visitByExpression(element, state);
    }

    @Override
    public TranspilerNode visitConcatenate(Concatenate element, State state) {
        new NaryExpressionNode<Concatenate>(state, element);
        return super.visitConcatenate(element, state);
    }

    @Override
    public TranspilerNode visitContextDef(ContextDef element, State state) {
        new ContextDefNode(state, element);
        return super.visitContextDef(element, state);
    }

    @Override
    public TranspilerNode visitCount(Count element, State state) {
        new CountNode(state, element);
        return super.visitCount(element, state);
    }

    @Override
    public TranspilerNode visitDateTime(DateTime element, State state) {
        new DateTimeNode(state, element);
        return super.visitDateTime(element, state);
    }

    @Override
    public TranspilerNode visitDifferenceBetween(DifferenceBetween element, State state) {
        new BinaryExpressionNode<DifferenceBetween>(state, element);
        return super.visitDifferenceBetween(element, state);
    }

    @Override
    public TranspilerNode visitDivide(Divide element, State state) {
        new BinaryExpressionNode<Divide>(state, element);
        return super.visitDivide(element, state);
    }

    @Override
    public TranspilerNode visitExpressionDef(ExpressionDef element, State state) {
        if (element instanceof FunctionDef) {
            return super.visitExpressionDef((FunctionDef) element, state);
        }
        new ExpressionDefNode<ExpressionDef>(state, element);
        return super.visitExpressionDef(element, state);
    }

    @Override
    public TranspilerNode visitExpressionRef(ExpressionRef element, State state) {
        new ExpressionRefNode(state, element);
        return super.visitExpressionRef(element, state);
    }

    @Override
    public TranspilerNode visitFlatten(Flatten element, State state) {
        new FlattenNode(state, element);
        return super.visitFlatten(element, state);
    }

    @Override
    public TranspilerNode visitFunctionDef(FunctionDef element, State state) {
        new FunctionDefNode(state, element);
        return super.visitFunctionDef(element, state);
    }

    @Override
    public TranspilerNode visitFunctionRef(FunctionRef element, State state) {
        new FunctionRefNode(state, element);
        return super.visitFunctionRef(element, state);
    }

    @Override
    public TranspilerNode visitIdentifierRef(IdentifierRef element, State state) {
        new IdentifierRefNode(state, element);
        return super.visitIdentifierRef(element, state);
    }

    @Override
    public TranspilerNode visitIn(In element, State state) {
        new BinaryExpressionNode<In>(state, element);
        return super.visitIn(element, state);
    }

    @Override
    public TranspilerNode visitInValueSet(InValueSet element, State state) {
        new InValueSetNode(state, element);
        return super.visitInValueSet(element, state);
    }

    @Override
    public TranspilerNode visitIncludeDef(IncludeDef element, State state) {
        new IncludeDefNode(state, element);
        return super.visitIncludeDef(element, state);
    }

    @Override
    public TranspilerNode visitIntervalTypeSpecifier(IntervalTypeSpecifier element, State state) {
        new IntervalTypeSpecifierNode(state, element);
        return super.visitIntervalTypeSpecifier(element, state);
    }

    @Override
    public TranspilerNode visitLessOrEqual(LessOrEqual element, State state) {
        new BinaryExpressionNode<LessOrEqual>(state, element);
        return super.visitLessOrEqual(element, state);
    }

    @Override
    public TranspilerNode visitLetClause(LetClause element, State state) {
        new LetClauseNode(state, element);
        return super.visitLetClause(element, state);
    }

    @Override
    public TranspilerNode visitLibrary(Library element, State state) {
        new LibraryNode(state, element);
        return super.visitLibrary(element, state);
    }

    @Override
    public TranspilerNode visitList(List element, State state) {
        new ListNode(state, element);
        return super.visitList(element, state);
    }

    @Override
    public TranspilerNode visitListTypeSpecifier(ListTypeSpecifier element, State state) {
        new ListTypeSpecifierNode(state, element);
        return super.visitListTypeSpecifier(element, state);
    }

    @Override
    public TranspilerNode visitLiteral(Literal element, State state) {
        new LiteralNode(state, element);
        return super.visitLiteral(element, state);
    }

    @Override
    public TranspilerNode visitMultiply(Multiply element, State state) {
        new BinaryExpressionNode<Multiply>(state, element);
        return super.visitMultiply(element, state);
    }

    @Override
    public TranspilerNode visitOperandDef(OperandDef element, State state) {
        new OperandDefNode(state, element);
        return super.visitOperandDef(element, state);
    }

    @Override
    public TranspilerNode visitOperandRef(OperandRef element, State state) {
        new OperandRefNode(state, element);
        return super.visitOperandRef(element, state);
    }

    @Override
    public TranspilerNode visitOr(Or element, State state) {
        new BinaryExpressionNode<Or>(state, element);
        return super.visitOr(element, state);
    }

    @Override
    public TranspilerNode visitParameterDef(ParameterDef element, State state) {
        new ParameterDefNode(state, element);
        return super.visitParameterDef(element, state);
    }

    @Override
    public TranspilerNode visitParameterRef(ParameterRef element, State state) {
        new ParameterRefNode(state, element);
        return super.visitParameterRef(element, state);
    }

    @Override
    public TranspilerNode visitProperty(Property element, State state) {
        if (element instanceof Search) {
            return super.visitSearch((Search) element, state);
        }
        new PropertyNode(state, element);
        return super.visitProperty(element, state);
    }

    @Override
    public TranspilerNode visitQueryLetRef(QueryLetRef element, State state) {
        new QueryLetRefNode(state, element);
        return super.visitQueryLetRef(element, state);
    }

    @Override
    public TranspilerNode visitQuery(Query element, State state) {
        new QueryNode(state, element);
        return super.visitQuery(element, state);
    }

    @Override
    public TranspilerNode visitRetrieve(Retrieve element, State state) {
        new RetrieveNode(state, element);
        return super.visitRetrieve(element, state);
    }

    @Override
    public TranspilerNode visitReturnClause(ReturnClause element, State state) {
        new ReturnClauseNode(state, element);
        return super.visitReturnClause(element, state);
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
        new SortByItemNode<SortByItem>(state, element);
        return super.visitSortByItem(element, state);
    }

    @Override
    public TranspilerNode visitSortClause(SortClause element, State state) {
        new SortClauseNode(state, element);
        return super.visitSortClause(element, state);
    }

    @Override
    public TranspilerNode visitSubtract(Subtract element, State state) {
        new BinaryExpressionNode<Subtract>(state, element);
        return super.visitSubtract(element, state);
    }

    @Override
    public TranspilerNode visitTuple(Tuple element, State state) {
        new TupleNode(state, element);
        return super.visitTuple(element, state);
    }

    @Override
    public TranspilerNode visitTupleElement(TupleElement element, State state) {
        new TupleElementNode(state, element);
        return super.visitTupleElement(element, state);
    }

    @Override
    public TranspilerNode visitNamedTypeSpecifier(NamedTypeSpecifier element, State state) {
        new NamedTypeSpecifierNode(state, element);
        return super.visitNamedTypeSpecifier(element, state);
    }

    @Override
    public TranspilerNode visitUnion(Union element, State state) {
        new UnionNode(state, element);
        return super.visitUnion(element, state);
    }

    @Override
    public TranspilerNode visitUsingDef(UsingDef element, State state) {
        new UsingDefNode(state, element);
        return super.visitUsingDef(element, state);
    }

    @Override
    public TranspilerNode visitValueSetDef(ValueSetDef element, State state) {
        new ValueSetDefNode(state, element);
        return super.visitValueSetDef(element, state);
    }
}
