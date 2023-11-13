package gov.va.transpiler.sparksql.node;

public class Default extends Ary {

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
            for(AbstractCQLNode child : getChildren()) {
                success |= child.print(outputWriter);
            }
            outputWriter.lowerIndentLevel();
            outputWriter.printFullLine("-- ]");
        }
        return success;
    }
}
