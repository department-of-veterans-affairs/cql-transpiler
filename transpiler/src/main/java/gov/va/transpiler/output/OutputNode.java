package gov.va.transpiler.output;

public abstract class OutputNode {

    public void addChild(OutputNode child) {
        throw new IllegalArgumentException("[" + child.asOneLine() + "] cannot be a child of this class.");
    }

    /**
     * @return If this node and all its children can be returned as a single line of text, returns that. Otherwise returns null.
     */
    public String asOneLine() {
        return null;
    }

    /**
     * @param elmConverterState Prints this node and its children to the provided {@link OutputWriter}.
     */
    public void print(OutputWriter outputWriter) {
        throw new IllegalArgumentException("This node can never be printed to [" + outputWriter + "]");
    }
}
