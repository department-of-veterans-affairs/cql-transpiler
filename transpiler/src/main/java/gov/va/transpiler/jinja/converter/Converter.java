package gov.va.transpiler.jinja.converter;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Literal;
import org.hl7.elm.r1.UsingDef;

import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.ary.LibraryNode;
import gov.va.transpiler.jinja.node.ary.UnsupportedNode;
import gov.va.transpiler.jinja.node.leaf.LiteralNode;
import gov.va.transpiler.jinja.node.leaf.UsingDefNode;
import gov.va.transpiler.jinja.node.unary.ExpressionDefNode;

public class Converter extends ElmBaseLibraryVisitor<TranspilerNode, State> {

    public TranspilerNode convert(Library library, State state) {
        return visitElement(library, state);
    }

    @Override
    protected TranspilerNode defaultResult(Trackable elm, State state) {
        var current = state.getCurrentNode();
        if (current == null) {
            if (elm == null) {
                current = new DisabledNode();
            } else {
                current = new UnsupportedNode(elm);
            }
        } else {
            state.setCurrentNode(null);
        }
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
        var currentNode = new LibraryNode(library);
        state.setCurrentNode(currentNode);
        var returnval = super.visitLibrary(library, state);
        return returnval;
    }

    @Override
    public TranspilerNode visitUsingDef(UsingDef usingdef, State state) {
        var currentNode = new UsingDefNode(usingdef);
        state.setCurrentNode(currentNode);
        return super.visitUsingDef(usingdef, state);
    }

    @Override
    public TranspilerNode visitExpressionDef(ExpressionDef expressiondef, State state) {
        var currentNode = new ExpressionDefNode(expressiondef);
        state.setCurrentNode(currentNode);
        return super.visitExpressionDef(expressiondef, state);
    }

    @Override
    public TranspilerNode visitLiteral(Literal literal, State state) {
        var currentNode = new LiteralNode(literal);
        state.setCurrentNode(currentNode);
        return super.visitLiteral(literal, state);
    }
}