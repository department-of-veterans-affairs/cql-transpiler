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
    public void addChild(OutputNode child) {
        children.add(child);
    }

    @Override
    public String asOneLine() {
        String builder = "# Unsupported node " + name + " [ ";
        for (var child : children) {
            builder += child.asOneLine();
        }
        builder += " ]";
        return builder;
    }

    @Override
    public void print(OutputWriter outputWriter) {
        outputWriter.addLine("# Unsupported node " + name + " [");
        outputWriter.raiseIndentLevel();
        for(OutputNode child : children) {
            String oneLine = child.asOneLine();
            if (oneLine != null) {
                outputWriter.addLine(oneLine);
            } else {
                child.print(outputWriter);
            }
        }
        outputWriter.lowerIndentLevel();
        outputWriter.addLine("# ]");
    }
}
