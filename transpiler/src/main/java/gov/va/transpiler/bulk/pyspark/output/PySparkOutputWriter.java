package gov.va.transpiler.bulk.pyspark.output;

import gov.va.transpiler.output.OutputWriter;

public class PySparkOutputWriter extends OutputWriter{

    private final String toIndentWith;
    private final String toLineBreakWith;
    private final StringBuilder document;

    public PySparkOutputWriter(int startingIndentLevel, String toIndentWith, String toLineBreakWith) {
        if (startingIndentLevel < 0) {
            throw new IllegalArgumentException("Cannot have negative indent level");
        }
        if (toIndentWith == null) {
            throw new IllegalArgumentException("Must set indent type");
        }
        if (toLineBreakWith == null) {
            throw new IllegalArgumentException("Must set line break type.");
        }
        setIndentLevel(startingIndentLevel);
        this.toIndentWith = toIndentWith;
        this.toLineBreakWith = toLineBreakWith;
        document = new StringBuilder();
    }

    @Override
    public void addLine(String line) {
        for (int i = 0; i < getIndentLevel(); i++) {
            document.append(toIndentWith);
        }
        document.append(line);
        document.append(toLineBreakWith);
    }

    @Override
    public String getDocumentContents() {
        return document.toString();
    }
}
