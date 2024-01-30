package gov.va.transpiler.jinja.converter;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.Add;
import org.hl7.elm.r1.After;
import org.hl7.elm.r1.AliasedQuerySource;
import org.hl7.elm.r1.As;
import org.hl7.elm.r1.Before;
import org.hl7.elm.r1.BinaryExpression;
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
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.List;
import org.hl7.elm.r1.Literal;
import org.hl7.elm.r1.Multiply;
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
import org.hl7.elm.r1.ValueSetDef;
import org.hl7.elm.r1.ValueSetRef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.ary.LibraryNode;
import gov.va.transpiler.jinja.node.expression.BinaryExpressionNode;
import gov.va.transpiler.jinja.node.leaf.LiteralNode;
import gov.va.transpiler.jinja.node.leaf.UsingDefNode;
import gov.va.transpiler.jinja.node.leaf.ValueSetDefNode;
import gov.va.transpiler.jinja.node.unary.ExpressionDefNode;
import gov.va.transpiler.jinja.node.unsupported.DisabledNode;
import gov.va.transpiler.jinja.node.unsupported.DefaultNode;
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
    public TranspilerNode visitBinaryExpression(BinaryExpression element, State state) {
        new BinaryExpressionNode<>(state, element);
        return super.visitBinaryExpression(element, state);
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
    public TranspilerNode visitIncludeDef(IncludeDef element, State state) {
        var current = new DisabledNode(state);
        state.setCurrentNode(null);
        return current;
    }

    @Override
    public TranspilerNode visitLibrary(Library element, State state) {
        new LibraryNode(state, element);
        return super.visitLibrary(element, state);
    }

    @Override
    public TranspilerNode visitLiteral(Literal element, State state) {
        new LiteralNode(state, element);
        return super.visitLiteral(element, state);
    }

    @Override
    public TranspilerNode visitParameterDef(ParameterDef element, State state) {
        var current = new DisabledNode(state);
        state.setCurrentNode(null);
        return current;
    }

    @Override
    public TranspilerNode visitTypeSpecifier(TypeSpecifier element, State state) {
        var current = new DisabledNode(state);
        state.setCurrentNode(null);
        return current;
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
