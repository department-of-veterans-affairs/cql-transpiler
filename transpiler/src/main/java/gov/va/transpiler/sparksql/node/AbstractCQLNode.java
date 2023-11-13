package gov.va.transpiler.sparksql.node;

public abstract class AbstractCQLNode {

    private Object cqlNodeEquivalent;
    private String name;

    /**
     * @param child Child to add to this node.
     * @return True if the child was added. False otherwise.
     */
    public abstract boolean addChild(AbstractCQLNode child);

    /**
     * @return If this node and all its children can be returned as a single line of text, returns that. Otherwise returns null.
     */
    public abstract String asOneLine();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean appliesToNode() {
        return true;
    }

    public Object getCqlNodeEquivalent() {
        return cqlNodeEquivalent;
    }

    public void setCqlNodeEquivalent(Object cqlNodeEquivalent) {
        this.cqlNodeEquivalent = cqlNodeEquivalent;
    }

    /**
     * @param elmConverterState Prints this node and its children to the provided {@link OutputWriter}.
     * @return True if printing was succesful, false otherwise.
     */
    public boolean print(OutputWriter outputWriter) {
        if (asOneLine() != null) {
            if (outputWriter.isCurrentLineAlreadyStarted()) {
                outputWriter.addText(asOneLine());
                outputWriter.endLine();
            } else {
                outputWriter.printFullLine(asOneLine());
            }
            return true;
        }
        return false;
    }

    /**
     * We treat tables differently from simple values.
     */
    public boolean isTable() {
        return false;
    }
}
