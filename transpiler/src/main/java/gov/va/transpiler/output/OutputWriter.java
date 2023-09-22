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

    public abstract void addLine(String line);
    public abstract String getDocumentContents();
}
