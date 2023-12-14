package gov.va.transpiler.jinja.node.unary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.FunctionDef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.leaf.OperandDefNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class FunctionDefNode extends Unary<FunctionDef> {

    private List<OperandDefNode> operandDefList = new ArrayList<>();
    private Map<String, OperandDefNode> nameToOperandDefMap = new LinkedHashMap<>();

    public FunctionDefNode(State state, FunctionDef t) {
        super(state, t);
    }

    protected String getNameFromOperand(OperandDefNode operandDefNode) {
        return operandDefNode.getCqlEquivalent().getName();
    }

    @Override
    public Segment toSegment() {
        var functionFileSegment = new Segment(this);
        functionFileSegment.setLocator(getCqlEquivalent().getLocator());
        var functionContentsSegment = new Segment(getChild());
        functionFileSegment.addSegmentToBody(functionContentsSegment);

        // Create the macro definition
        var sb = new StringBuilder();
        sb.append("{% macro ");
        sb.append(referenceIs());
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

        // contents of function
        functionContentsSegment.addSegmentToBody(getChild().toSegment());

        // finish macro
        functionContentsSegment.setTail("{% endmacro %}");
        return functionFileSegment;
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
    public boolean isTable() {
        return getChild().isTable();
    }

    @Override
    public boolean isSimpleValue() {
        return getChild().isTable();
    }

    @Override
    public String referenceIs() {
        return getCqlEquivalent().getName();
    }

    @Override
    public PrintType getPrintType() {
        return PrintType.File;
    }
}
