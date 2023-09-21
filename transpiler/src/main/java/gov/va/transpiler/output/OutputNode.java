package gov.va.transpiler.output;

public abstract class OutputNode {

    /**
     * @param child Child to add to this node.
     * @return True if the child was added. False otherwise.
     */
    public abstract boolean addChild(OutputNode child);

    /**
     * @return If this node and all its children can be returned as a single line of text, returns that. Otherwise returns null.
     */
    public abstract String asOneLine();

    /**
     * @param elmConverterState Prints this node and its children to the provided {@link OutputWriter}.
     * @return True if printing was succesful, false otherwise.
     */
    public boolean print(OutputWriter outputWriter) {
        if (asOneLine() != null) {
            outputWriter.addLine(asOneLine());
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String asOneLine = asOneLine();
        return asOneLine == null ? super.toString() : asOneLine;
    }
}