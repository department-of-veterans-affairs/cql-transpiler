package gov.va.transpiler.jinja.node.unary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.FunctionDef;

import gov.va.transpiler.jinja.node.ReferenceableNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.leaf.OperandDefNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class FunctionDefNode extends Unary<FunctionDef> implements ReferenceableNode {

    public static final String REFERENCE_TYPE = "FunctionDef";

    private List<OperandDefNode> operandDefList = new ArrayList<>();
    private Map<String, OperandDefNode> nameToOperandDefMap = new LinkedHashMap<>();

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
            nameToOperandDefMap.put(getNameFromOperand((OperandDefNode) child), (OperandDefNode) child);
        } else {
            super.addChild(child);
        }
    }

    @Override
    public Segment toSegment() {
        var functionFileSegment = new Segment();
        functionFileSegment.setLocator(getCqlEquivalent().getLocator());
        functionFileSegment.setFileLocation(getTargetFileLocation());
        functionFileSegment.setPrintType(PrintType.File);

        // wrap function with macro definition
        var functionContentsSegment = new Segment();
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
        functionContentsSegment.setTail("{% endmacro %}");

        // nest segments
        functionContentsSegment.addChild(getChild().toSegment());
        functionFileSegment.addChild(functionContentsSegment);

        return functionFileSegment;
    }

    @Override
    public String referenceType() {
        return REFERENCE_TYPE;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }

    @Override
    public String getTargetFileLocation() {
        return super.getTargetFileLocation() + Standards.FOLDER_SEPARATOR +  referenceName();
    }
}
