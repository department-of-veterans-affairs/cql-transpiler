package gov.va.transpiler.sparksql.node;

public class OutputWriter {

    private boolean currentLineAlreadyStarted;
    private final String toIndentWith;
    private final String toLineBreakWith;
    private final StringBuilder document;
    private int indentLevel = 0;

    public OutputWriter(int startingIndentLevel, String toIndentWith, String toLineBreakWith) {
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

    public void printFullLine(String text) {
        if (!isCurrentLineAlreadyStarted()) {
            startLine();
            setCurrentLineAlreadyStarted(true);
        }
        addText(text);
        endLine();
    }

    public void setIndentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
    }

    public void raiseIndentLevel() {
        indentLevel++;
    }

    public void lowerIndentLevel() {
        indentLevel--;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public String getDocumentContents() {
        return document.toString();
    }

    public boolean isCurrentLineAlreadyStarted() {
       return currentLineAlreadyStarted;
    }

    protected void setCurrentLineAlreadyStarted(boolean started) {
        currentLineAlreadyStarted = started;
    }

    public void startLine() {
        if (isCurrentLineAlreadyStarted()) {
            throw new IllegalStateException("Line already started");
        }
        setCurrentLineAlreadyStarted(true);
        for (int i = 0; i < getIndentLevel(); i++) {
            document.append(toIndentWith);
        }
    }

    public void addText(String text) {
        document.append(text);
    }

    public void endLine() {
        setCurrentLineAlreadyStarted(false);
        document.append(toLineBreakWith);
    }
}
