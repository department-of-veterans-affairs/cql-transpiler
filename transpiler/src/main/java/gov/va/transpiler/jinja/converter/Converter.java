package gov.va.transpiler.jinja.converter;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.*;

import gov.va.transpiler.jinja.node.DefaultNode;
import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.TupleElementNode;
import gov.va.transpiler.jinja.node.trackable.element.ContextDefNode;
import gov.va.transpiler.jinja.node.trackable.element.LibraryNode;
import gov.va.transpiler.jinja.node.trackable.element.OperandDefNode;
import gov.va.transpiler.jinja.node.trackable.element.UsingDefNode;
import gov.va.transpiler.jinja.node.trackable.element.ValueSetDefNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.ExpressionRefNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.FunctionRefNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.ListNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.LiteralNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.OperandRefNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.PropertyNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.RetrieveNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.TupleNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.binaryexpression.BinaryExpressionNode;
import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.naryexpression.NaryExpressionNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.ExpressionDefNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.FunctionDefNode;
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
    public TranspilerNode visitAdd(Add element, State state) {
        new BinaryExpressionNode<Add>(state, element);
        return super.visitAdd(element, state);
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
    public TranspilerNode visitIncludeDef(IncludeDef element, State state) {
        state.setCurrentNode(null);
        return super.visitIncludeDef(element, state);
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
    public TranspilerNode visitList(List element, State state) {
        new ListNode(state, element);
        return super.visitList(element, state);
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
    public TranspilerNode visitParameterDef(ParameterDef element, State state) {
        state.setCurrentNode(null);
        return super.visitParameterDef(element, state);
    }

    @Override
    public TranspilerNode visitProperty(Property element, State state) {
        new PropertyNode(state, element);
        return super.visitProperty(element, state);
    }

    @Override
    public TranspilerNode visitRetrieve(Retrieve element, State state) {
        new RetrieveNode(state, element);
        return super.visitRetrieve(element, state);
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
