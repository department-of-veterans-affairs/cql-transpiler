package gov.va.transpiler.jinja.converter;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.FunctionDef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Literal;
import org.hl7.elm.r1.OperandDef;
import org.hl7.elm.r1.TypeSpecifier;
import org.hl7.elm.r1.UsingDef;

import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.ary.LibraryNode;
import gov.va.transpiler.jinja.node.ary.UnsupportedNode;
import gov.va.transpiler.jinja.node.leaf.LiteralNode;
import gov.va.transpiler.jinja.node.leaf.OperandDefNode;
import gov.va.transpiler.jinja.node.leaf.UsingDefNode;
import gov.va.transpiler.jinja.node.unary.ExpressionDefNode;
import gov.va.transpiler.jinja.node.unary.FunctionDefNode;
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
    public TranspilerNode visitLibrary(Library library, State state) {
        new LibraryNode(state, library);
        var returnval = super.visitLibrary(library, state);
        return returnval;
    }

    @Override
    public TranspilerNode visitUsingDef(UsingDef usingdef, State state) {
        new UsingDefNode(state, usingdef);
        return super.visitUsingDef(usingdef, state);
    }

    @Override
    public TranspilerNode visitExpressionDef(ExpressionDef expressionDef, State state) {
        if (expressionDef instanceof FunctionDef) {
            return super.visitExpressionDef((FunctionDef) expressionDef, state);
        }
        new ExpressionDefNode(state, expressionDef);
        return super.visitExpressionDef(expressionDef, state);
    }

    @Override
    public TranspilerNode visitFunctionDef(FunctionDef functionDef, State state) {
        new FunctionDefNode(state, functionDef);
        return super.visitFunctionDef(functionDef, state);
    }

    @Override
    public TranspilerNode visitOperandDef(OperandDef operandDef, State state) {
        new OperandDefNode(state, operandDef);
        return super.visitOperandDef(operandDef, state);
    }

    @Override
    public TranspilerNode visitTypeSpecifier(TypeSpecifier typeSpecifier, State state) {
        var current = new DisabledNode(state);
        state.setCurrentNode(null);
        return current;
    }

    @Override
    public TranspilerNode visitLiteral(Literal literal, State state) {
        new LiteralNode(state, literal);
        return super.visitLiteral(literal, state);
    }
}
