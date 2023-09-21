package gov.va.transpiler.output;

import java.util.ArrayList;
import java.util.List;

public class DefaultOutputNode extends OutputNode {

    private final String name;
    private final List<OutputNode> children;

    public DefaultOutputNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    @Override
    public boolean addChild(OutputNode child) {
        children.add(child);
        return true;
    }

    @Override
    public String asOneLine() {
        String builder = "# Unsupported node " + name + " [ ";
        String childAsOneLine;
        if (children.isEmpty()) {}
        else if (children.size() == 1 && (childAsOneLine = children.get(0).asOneLine()) != null) {
            builder += childAsOneLine;
        } else {
            return null;
        }
        builder += " ]";
        return builder;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        if (!super.print(outputWriter)) {
            outputWriter.addLine("# Unsupported node " + name + " [");
            outputWriter.raiseIndentLevel();
            for(OutputNode child : children) {
                child.print(outputWriter);
            }
            outputWriter.lowerIndentLevel();
            outputWriter.addLine("# ]");
        }
        return true;
    }
}
