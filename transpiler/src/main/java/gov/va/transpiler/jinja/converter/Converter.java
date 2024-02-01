package gov.va.transpiler.jinja.converter;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.*;

import gov.va.transpiler.jinja.node.DefaultNode;
import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.element.ExpressionDefNode;
import gov.va.transpiler.jinja.node.element.LibraryNode;
import gov.va.transpiler.jinja.node.element.UsingDefNode;
import gov.va.transpiler.jinja.node.element.ValueSetDefNode;
import gov.va.transpiler.jinja.node.element.expression.ExpressionRefNode;
import gov.va.transpiler.jinja.node.element.expression.ListNode;
import gov.va.transpiler.jinja.node.element.expression.LiteralNode;
import gov.va.transpiler.jinja.node.element.expression.binaryexpression.BinaryExpressionNode;
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
    public TranspilerNode visitParameterDef(ParameterDef element, State state) {
        state.setCurrentNode(null);
        return super.visitParameterDef(element, state);
    }

    @Override
    public TranspilerNode visitExpressionRef(ExpressionRef element, State state) {
        new ExpressionRefNode(state, element);
        return super.visitExpressionRef(element, state);
    }

    @Override
    public TranspilerNode visitTypeSpecifier(TypeSpecifier element, State state) {
        state.setCurrentNode(null);
        return super.visitTypeSpecifier(element, state);
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
