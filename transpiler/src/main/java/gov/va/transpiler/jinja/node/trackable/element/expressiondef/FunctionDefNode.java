package gov.va.transpiler.jinja.node.trackable.element.expressiondef;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.FunctionDef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.OperandDefNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class FunctionDefNode extends ExpressionDefNode<FunctionDef> {

    public static final String REFERENCE_TYPE = "FunctionDef";

    public List<OperandDefNode> operandDefNodeList = new ArrayList<>();

    public FunctionDefNode(State state, FunctionDef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof OperandDefNode) {
            operandDefNodeList.add((OperandDefNode) child);
        } else {
            super.addChild(child);
        }
    }

    @Override
    public Segment toSegment() {
        var enclosingSegment = new Segment();
        enclosingSegment.setPrintType(PrintType.Line);
        enclosingSegment.setLocator(getCqlEquivalent().getLocator());

        // opening brackets to macro
        var openMacro = joinChildren(operandDefNodeList, "{% macro " + referenceName() + "(", ") %}", "", "", ", ");
        enclosingSegment.addChild(openMacro);

        // inside of function
        var internal = new Segment();
        internal.setHead("{{ ");
        internal.addChild(childToSegment(getChild()));
        internal.setTail(" }}");
        enclosingSegment.addChild(internal);

        // closing brackets to macro
        var closeMacro = new Segment();
        closeMacro.setHead("{% endmacro %}");
        enclosingSegment.addChild(closeMacro);

        return enclosingSegment;
    }

    @Override
    public String referenceType() {
        return REFERENCE_TYPE;
    }

    @Override
    public Type getType() {
        return getChild().getType();
    }
}
