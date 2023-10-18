package gov.va.transpiler.bulk.sparksql;

import gov.va.transpiler.node.OutputWriter;

public class SparkSQLOutputWriter extends OutputWriter{

    private boolean currentLineAlreadyStarted;
    private final String toIndentWith;
    private final String toLineBreakWith;
    private final StringBuilder document;

    public SparkSQLOutputWriter(int startingIndentLevel, String toIndentWith, String toLineBreakWith) {
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
    public String getDocumentContents() {
        return document.toString();
    }

    @Override
    public boolean isCurrentLineAlreadyStarted() {
       return currentLineAlreadyStarted;
    }

    @Override
    protected void setCurrentLineAlreadyStarted(boolean started) {
        currentLineAlreadyStarted = started;
    }

    @Override
    public void startLine() {
        if (isCurrentLineAlreadyStarted()) {
            throw new IllegalStateException("Line already started");
        }
        setCurrentLineAlreadyStarted(true);
        for (int i = 0; i < getIndentLevel(); i++) {
            document.append(toIndentWith);
        }
    }

    @Override
    public void addText(String text) {
        document.append(text);
    }

    @Override
    public void endLine() {
        setCurrentLineAlreadyStarted(false);
        document.append(toLineBreakWith);
    }
}
