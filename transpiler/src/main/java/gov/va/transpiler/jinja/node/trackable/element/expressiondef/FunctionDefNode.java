package gov.va.transpiler.jinja.node.trackable.element.expressiondef;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.FunctionDef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.node.trackable.element.OperandDefNode;
import gov.va.transpiler.jinja.node.trackable.element.typespecifier.TypeSpecifierNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class FunctionDefNode extends ExpressionDefNode<FunctionDef> {

    public static final String REFERENCE_TYPE = "FunctionDef";

    public List<OperandDefNode> operandDefNodeList = new ArrayList<>();
    public TypeSpecifierNode<?> typeSpecifierNode;

    public FunctionDefNode(State state, FunctionDef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof OperandDefNode) {
            operandDefNodeList.add((OperandDefNode) child);
        } else if (child instanceof TypeSpecifierNode) {
            if (typeSpecifierNode == null) {
                typeSpecifierNode = (TypeSpecifierNode<?>) child;
            } else {
                throw new UnsupportedChildNodeException(this, child);
            }
        } else {
            super.addChild(child);
        }
    }

    @Override
    public Segment toSegment() {
        var enclosingSegment = new Segment("");
        enclosingSegment.setPrintType(PrintType.Line);
        enclosingSegment.setLocator(getCqlEquivalent().getLocator());
        // macro start segment - including function parameters so they can be referenced by children
        var macroStart = joinChildren(operandDefNodeList, "{% macro " + referenceName() + "(state, ", ") %}", "", "", ", ");
        enclosingSegment.addChild(macroStart);
        // macro middle - wrap the dictionary representation of this object in a macro block for calling
        var macroMiddle = new Segment("{{ OperatorHandler.print(state, ", ") }}", PrintType.Inline);
        macroMiddle.addChild(super.toSegment());
        // macro end segment
        var macroEnd = new Segment("{% endmacro %}");
        enclosingSegment.addChild(macroEnd);
        return enclosingSegment;
    }

    @Override
    public String referenceType() {
        return REFERENCE_TYPE;
    }
}
