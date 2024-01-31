package gov.va.transpiler.jinja.converter;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.*;

import gov.va.transpiler.jinja.node.DefaultNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.ary.LibraryNode;
import gov.va.transpiler.jinja.node.expression.BinaryExpressionNode;
import gov.va.transpiler.jinja.node.expression.ExpressionRefNode;
import gov.va.transpiler.jinja.node.leaf.LiteralNode;
import gov.va.transpiler.jinja.node.leaf.UsingDefNode;
import gov.va.transpiler.jinja.node.leaf.ValueSetDefNode;
import gov.va.transpiler.jinja.node.unary.ExpressionDefNode;
import gov.va.transpiler.jinja.node.unsupported.DisabledNode;
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
    public TranspilerNode visitExpressionRef(ExpressionRef element, State state) {
        new ExpressionRefNode(state, element);
        return super.visitExpressionRef(element, state);
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
