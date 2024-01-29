package gov.va.transpiler.sparkjinja.node.unary;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.FunctionDef;

import gov.va.transpiler.sparkjinja.node.ReferenceableNode;
import gov.va.transpiler.sparkjinja.node.TranspilerNode;
import gov.va.transpiler.sparkjinja.node.leaf.OperandDefNode;
import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.printing.Segment.PrintType;
import gov.va.transpiler.sparkjinja.standards.Standards;
import gov.va.transpiler.sparkjinja.state.State;

public class FunctionDefNode extends Unary<FunctionDef> implements ReferenceableNode {

    public static final String REFERENCE_TYPE = "FunctionDef";

    private List<OperandDefNode> operandDefList = new ArrayList<>();

    public FunctionDefNode(State state, FunctionDef t) {
        super(state, t);
    }

    protected String getNameFromOperand(OperandDefNode operandDefNode) {
        return operandDefNode.getCqlEquivalent().getName();
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof OperandDefNode) {
            operandDefList.add((OperandDefNode) child);
        } else {
            super.addChild(child);
        }
    }

    @Override
    public Segment toSegment() {

        // wrap function with macro definition
        var functionContentsSegment = new Segment();
        functionContentsSegment.setPrintType(PrintType.Line);
        var sb = new StringBuilder();
        sb.append("{% macro ");
        sb.append(referenceName());
        sb.append(" (");
        boolean first = true;;
        for (var operand : operandDefList) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(getNameFromOperand(operand));
        }
        sb.append(") %}");
        functionContentsSegment.setHead(sb.toString());
        // nest segments
        var childSegment = getChild().toSegment();
        functionContentsSegment.addChild(childSegment);
        functionContentsSegment.setTail("{% endmacro %}");
        functionContentsSegment.setPrintType(PrintType.Inline);
        functionContentsSegment.setLocator(getCqlEquivalent().getLocator());

        return functionContentsSegment;
    }

    @Override
    public String referenceType() {
        return REFERENCE_TYPE;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName().replace(' ', '_');
    }

    @Override
    public String getTargetFileLocation() {
        return super.getTargetFileLocation() + Standards.FOLDER_SEPARATOR +  referenceName();
    }
}
