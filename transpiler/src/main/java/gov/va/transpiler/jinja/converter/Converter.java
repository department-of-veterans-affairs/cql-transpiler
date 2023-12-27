package gov.va.transpiler.jinja.converter;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.Add;
import org.hl7.elm.r1.After;
import org.hl7.elm.r1.AliasedQuerySource;
import org.hl7.elm.r1.As;
import org.hl7.elm.r1.Before;
import org.hl7.elm.r1.ByExpression;
import org.hl7.elm.r1.Concatenate;
import org.hl7.elm.r1.ContextDef;
import org.hl7.elm.r1.Count;
import org.hl7.elm.r1.DateFrom;
import org.hl7.elm.r1.DateTime;
import org.hl7.elm.r1.DifferenceBetween;
import org.hl7.elm.r1.Divide;
import org.hl7.elm.r1.End;
import org.hl7.elm.r1.Equal;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.ExpressionRef;
import org.hl7.elm.r1.Flatten;
import org.hl7.elm.r1.FunctionDef;
import org.hl7.elm.r1.FunctionRef;
import org.hl7.elm.r1.IdentifierRef;
import org.hl7.elm.r1.If;
import org.hl7.elm.r1.IncludeDef;
import org.hl7.elm.r1.Interval;
import org.hl7.elm.r1.IntervalTypeSpecifier;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.List;
import org.hl7.elm.r1.Literal;
import org.hl7.elm.r1.Multiply;
import org.hl7.elm.r1.NamedTypeSpecifier;
import org.hl7.elm.r1.Negate;
import org.hl7.elm.r1.Not;
import org.hl7.elm.r1.Null;
import org.hl7.elm.r1.OperandDef;
import org.hl7.elm.r1.OperandRef;
import org.hl7.elm.r1.ParameterDef;
import org.hl7.elm.r1.ParameterRef;
import org.hl7.elm.r1.Property;
import org.hl7.elm.r1.Query;
import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ReturnClause;
import org.hl7.elm.r1.SingletonFrom;
import org.hl7.elm.r1.SortByItem;
import org.hl7.elm.r1.SortClause;
import org.hl7.elm.r1.Start;
import org.hl7.elm.r1.Subtract;
import org.hl7.elm.r1.ToDate;
import org.hl7.elm.r1.ToDecimal;
import org.hl7.elm.r1.Tuple;
import org.hl7.elm.r1.TupleElement;
import org.hl7.elm.r1.TypeSpecifier;
import org.hl7.elm.r1.Union;
import org.hl7.elm.r1.UsingDef;

import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.ary.FunctionRefNode;
import gov.va.transpiler.jinja.node.ary.LibraryNode;
import gov.va.transpiler.jinja.node.ary.ListNode;
import gov.va.transpiler.jinja.node.ary.SortClauseNode;
import gov.va.transpiler.jinja.node.ary.TupleNode;
import gov.va.transpiler.jinja.node.ary.UnionNode;
import gov.va.transpiler.jinja.node.ary.binary.AddNode;
import gov.va.transpiler.jinja.node.ary.binary.AfterNode;
import gov.va.transpiler.jinja.node.ary.binary.BeforeNode;
import gov.va.transpiler.jinja.node.ary.binary.ConcatenateNode;
import gov.va.transpiler.jinja.node.ary.binary.DivideNode;
import gov.va.transpiler.jinja.node.ary.binary.EqualNode;
import gov.va.transpiler.jinja.node.ary.binary.MultiplyNode;
import gov.va.transpiler.jinja.node.ary.binary.SubtractNode;
import gov.va.transpiler.jinja.node.leaf.DateTimeNode;
import gov.va.transpiler.jinja.node.leaf.ExpressionRefNode;
import gov.va.transpiler.jinja.node.leaf.IdentifierRefNode;
import gov.va.transpiler.jinja.node.leaf.LiteralNode;
import gov.va.transpiler.jinja.node.leaf.OperandDefNode;
import gov.va.transpiler.jinja.node.leaf.OperandRefNode;
import gov.va.transpiler.jinja.node.leaf.QueryNode;
import gov.va.transpiler.jinja.node.leaf.RetrieveNode;
import gov.va.transpiler.jinja.node.leaf.UsingDefNode;
import gov.va.transpiler.jinja.node.unary.AliasedQuerySourceNode;
import gov.va.transpiler.jinja.node.unary.AsNode;
import gov.va.transpiler.jinja.node.unary.ByExpressionNode;
import gov.va.transpiler.jinja.node.unary.CountNode;
import gov.va.transpiler.jinja.node.unary.EndNode;
import gov.va.transpiler.jinja.node.unary.ExpressionDefNode;
import gov.va.transpiler.jinja.node.unary.FunctionDefNode;
import gov.va.transpiler.jinja.node.unary.NegateNode;
import gov.va.transpiler.jinja.node.unary.PropertyNode;
import gov.va.transpiler.jinja.node.unary.ReturnClauseNode;
import gov.va.transpiler.jinja.node.unary.SingletonFromNode;
import gov.va.transpiler.jinja.node.unary.SortByItemNode;
import gov.va.transpiler.jinja.node.unary.StartNode;
import gov.va.transpiler.jinja.node.unary.ToDecimalNode;
import gov.va.transpiler.jinja.node.unary.TupleElementNode;
import gov.va.transpiler.jinja.node.unsupported.DateFromNode;
import gov.va.transpiler.jinja.node.unsupported.DifferenceBetweenNode;
import gov.va.transpiler.jinja.node.unsupported.FlattenNode;
import gov.va.transpiler.jinja.node.unsupported.IfNode;
import gov.va.transpiler.jinja.node.unsupported.IncludeDefNode;
import gov.va.transpiler.jinja.node.unsupported.IntervalNode;
import gov.va.transpiler.jinja.node.unsupported.IntervalTypeSpecifierNode;
import gov.va.transpiler.jinja.node.unsupported.NamedTypeSpecifierNode;
import gov.va.transpiler.jinja.node.unsupported.NotNode;
import gov.va.transpiler.jinja.node.unsupported.NullNode;
import gov.va.transpiler.jinja.node.unsupported.ParameterDefNode;
import gov.va.transpiler.jinja.node.unsupported.ParameterRefNode;
import gov.va.transpiler.jinja.node.unsupported.ToDateNode;
import gov.va.transpiler.jinja.node.unsupported.UnsupportedNode;
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
                current = new DisabledNode(state);
            } else {
                current = new UnsupportedNode(state, elm);
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
    public TranspilerNode visitAdd(Add element, State state) {
        new AddNode(state, element);
        return super.visitAdd(element, state);
    }

    @Override
    public TranspilerNode visitAfter(After element, State state) {
        new AfterNode(state, element);
        return super.visitAfter(element, state);
    }

    @Override
    public TranspilerNode visitAliasedQuerySource(AliasedQuerySource element, State state) {
        new AliasedQuerySourceNode(state, element);
        return super.visitAliasedQuerySource(element, state);
    }

    @Override
    public TranspilerNode visitAs(As element, State state) {
        new AsNode(state, element);
        return super.visitAs(element, state);
    }

    @Override
    public TranspilerNode visitBefore(Before element, State state) {
        new BeforeNode(state, element);
        return super.visitBefore(element, state);
    }

    @Override
    public TranspilerNode visitByExpression(ByExpression element, State state) {
        new ByExpressionNode(state, element);
        return super.visitByExpression(element, state);
    }

    @Override
    public TranspilerNode visitConcatenate(Concatenate element, State state) {
        new ConcatenateNode(state, element);
        return super.visitConcatenate(element, state);
    }

    @Override
    public TranspilerNode visitContextDef(ContextDef element, State state) {
        var current = new DisabledNode(state);
        state.setCurrentNode(null);
        return current;
    }

    @Override
    public TranspilerNode visitCount(Count element, State state) {
        new CountNode(state, element);
        return super.visitCount(element, state);
    }

    @Override
    public TranspilerNode visitDateFrom(DateFrom element, State state) {
        new DateFromNode(state, element);
        return super.visitDateFrom(element, state);
    }

    @Override
    public TranspilerNode visitDateTime(DateTime element, State state) {
        new DateTimeNode(state, element);
        return super.visitDateTime(element, state);
    }

    @Override
    public TranspilerNode visitDifferenceBetween(DifferenceBetween element, State state) {
        new DifferenceBetweenNode(state, element);
        return super.visitDifferenceBetween(element, state);
    }

    @Override
    public TranspilerNode visitDivide(Divide element, State state) {
        new DivideNode(state, element);
        return super.visitDivide(element, state);
    }

    @Override
    public TranspilerNode visitEnd(End element, State state) {
        new EndNode(state, element);
        return super.visitEnd(element, state);
    }

    @Override
    public TranspilerNode visitEqual(Equal element, State state) {
        new EqualNode(state, element);
        return super.visitEqual(element, state);
    }

    @Override
    public TranspilerNode visitExpressionDef(ExpressionDef element, State state) {
        if (element instanceof FunctionDef) {
            return super.visitExpressionDef((FunctionDef) element, state);
        }
        new ExpressionDefNode(state, element);
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
    public TranspilerNode visitIf(If element, State state) {
        new IfNode(state, element);
        return super.visitIf(element, state);
    }

    @Override
    public TranspilerNode visitIncludeDef(IncludeDef element, State state) {
        new IncludeDefNode(state, element);
        return super.visitIncludeDef(element, state);
    }

    @Override
    public TranspilerNode visitInterval(Interval element, State state) {
        new IntervalNode(state, element);
        return super.visitInterval(element, state);
    }

    @Override
    public TranspilerNode visitIntervalTypeSpecifier(IntervalTypeSpecifier element, State state) {
        new IntervalTypeSpecifierNode(state, element);
        return super.visitIntervalTypeSpecifier(element, state);
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
    public TranspilerNode visitLiteral(Literal element, State state) {
        new LiteralNode(state, element);
        return super.visitLiteral(element, state);
    }

    @Override
    public TranspilerNode visitMultiply(Multiply element, State state) {
        new MultiplyNode(state, element);
        return super.visitMultiply(element, state);
    }

    @Override
    public TranspilerNode visitNamedTypeSpecifier(NamedTypeSpecifier element, State state) {
        new NamedTypeSpecifierNode(state, element);
        return super.visitNamedTypeSpecifier(element, state);
    }

    @Override
    public TranspilerNode visitNegate(Negate element, State state) {
        new NegateNode(state, element);
        return super.visitNegate(element, state);
    }

    @Override
    public TranspilerNode visitNot(Not element, State state) {
        new NotNode(state, element);
        return super.visitNot(element, state);
    }

    @Override
    public TranspilerNode visitNull(Null element, State state) {
        new NullNode(state, element);
        return super.visitNull(element, state);
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
        new PropertyNode(state, element);
        return super.visitProperty(element, state);
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
    public TranspilerNode visitSingletonFrom(SingletonFrom element, State state) {
        new SingletonFromNode(state, element);
        return super.visitSingletonFrom(element, state);
    }

    @Override
    public TranspilerNode visitStart(Start element, State state) {
        new StartNode(state, element);
        return super.visitStart(element, state);
    }

    @Override
    public TranspilerNode visitSortByItem(SortByItem element, State state) {
        new SortByItemNode(state, element);
        return super.visitSortByItem(element, state);
    }

    @Override
    public TranspilerNode visitSortClause(SortClause element, State state) {
        new SortClauseNode(state, element);
        return super.visitSortClause(element, state);
    }

    @Override
    public TranspilerNode visitSubtract(Subtract element, State state) {
        new SubtractNode(state, element);
        return super.visitSubtract(element, state);
    }

    @Override
    public TranspilerNode visitToDate(ToDate element, State state) {
        new ToDateNode(state, element);
        return super.visitToDate(element, state);
    }

    @Override
    public TranspilerNode visitToDecimal(ToDecimal element, State state) {
        new ToDecimalNode(state, element);
        return super.visitToDecimal(element, state);
    }

    @Override
    public TranspilerNode visitTupleElement(TupleElement element, State state) {
        new TupleElementNode(state, element);
        return super.visitTupleElement(element, state);
    }

    @Override
    public TranspilerNode visitTuple(Tuple element, State state) {
        new TupleNode(state, element);
        return super.visitTuple(element, state);
    }

    @Override
    public TranspilerNode visitTypeSpecifier(TypeSpecifier element, State state) {
        var current = new DisabledNode(state);
        state.setCurrentNode(null);
        return current;
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
}
