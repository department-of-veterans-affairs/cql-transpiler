package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.node.OutputWriter;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.node.OutputNode;

public class DefaultOutputNode extends AbstractNodeWithChildren<Trackable> {

    @Override
    public String asOneLine() {
        String builder = "Unsupported node " + getName();
        for (var child : getChildren()) {
            builder += "[";
            builder += child.asOneLine();
            builder += "]";
        }
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
            outputWriter.printFullLine("-- Unsupported node " + getName() + " [");
            outputWriter.raiseIndentLevel();
            for(OutputNode<? extends Trackable> child : getChildren()) {
                success |= child.print(outputWriter);
            }
            outputWriter.lowerIndentLevel();
            outputWriter.printFullLine("-- ]");
        }
        return success;
    }
}
