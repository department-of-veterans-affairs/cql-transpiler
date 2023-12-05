package gov.va.transpiler.jinja.converter;

import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Literal;

import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.ary.UnsupportedNode;
import gov.va.transpiler.jinja.node.leaf.LiteralNode;

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
        return aggregate;
    }

    @Override
    public TranspilerNode visitLiteral(Literal literal, State state) {
        var currentNode = new LiteralNode(literal);
        state.setCurrentNode(currentNode);
        return super.visitLiteral(literal, state);
    }
}
