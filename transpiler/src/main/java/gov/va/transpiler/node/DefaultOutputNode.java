package gov.va.transpiler.node;

import java.util.ArrayList;
import java.util.List;

import gov.va.transpiler.bulk.pyspark.OutputWriter;

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
        boolean success = true;
        // Try to add this node to the current line
        // Otherwise, try to add this node as a new, single line.
        // If that can't be done, print in full.
        if (outputWriter.isCurrentLineAlreadyStarted() && asOneLine() != null) {
            outputWriter.addText(asOneLine());
            outputWriter.endLine();
        } else if (!super.print(outputWriter)) {
            outputWriter.printFullLine("# Unsupported node " + name + " [");
            outputWriter.raiseIndentLevel();
            for(OutputNode child : children) {
                success |= child.print(outputWriter);
            }
            outputWriter.lowerIndentLevel();
            outputWriter.printFullLine("# ]");
        }
        return success;
    }
}
