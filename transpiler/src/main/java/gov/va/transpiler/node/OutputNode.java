package gov.va.transpiler.node;

import gov.va.transpiler.bulk.pyspark.OutputWriter;

public abstract class OutputNode {

    private Object cqlNodeEquivalent;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCqlNodeEquivalent() {
        return cqlNodeEquivalent;
    }

    public void setCqlNodeEquivalent(Object cqlNodeEquivalent) {
        this.cqlNodeEquivalent = cqlNodeEquivalent;
    }

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

    @Override
    public String toString() {
        String asOneLine = asOneLine();
        return asOneLine == null ? super.toString() : asOneLine;
    }
}
