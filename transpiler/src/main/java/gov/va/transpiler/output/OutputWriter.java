package gov.va.transpiler.output;

public abstract class OutputWriter {

    private int indentLevel = 0;

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

    /**
     * @param started Whether the current line has already been started
     */
    protected abstract void setCurrentLineAlreadyStarted(boolean started);
    /**
     * @return True if {@link #startLine()} has already been called for the current line. False otherwise.
     */
    public abstract boolean isCurrentLineAlreadyStarted();
    /**
     * Starts line with any indents necessary.
     */
    public abstract void startLine();
    /**
     * @param text Text to add to the current line.
     */
    public abstract void addText(String text);
    /**
     * Ends the current line by printing any text and adding a newline.
     * @param text Text to add to the current line.
     */
    public abstract void endLine();
    public void printFullLine(String text) {
        if (!isCurrentLineAlreadyStarted()) {
            startLine();
            setCurrentLineAlreadyStarted(true);
        }
        addText(text);
        endLine();
    }
    public abstract String getDocumentContents();
}
