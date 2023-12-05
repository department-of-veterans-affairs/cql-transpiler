package gov.va.transpiler.jinja.printing;

import java.util.ArrayList;
import java.util.List;

public class Segment {
    private static final String INDENT = "\t";
    private static final String NEWLINE = "\n";

    private boolean printThisInline = false;
    private boolean printInOwnFile = false;
    private String fileName = null;
    private String head = "";
    private List<Segment> body = new ArrayList<>();
    private String tail = "";

    public void setHead(String head) {
        this.head = head;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public void disableInlinePrinting() {
        printThisInline = true;
    }

    public void setToPrintInOwnFile() {
        printInOwnFile = true;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean canBePrintedInlineWithPreviousSegment() {
        return !printInOwnFile
            && printThisInline
            && body.stream().allMatch(Segment::canBePrintedInlineWithPreviousSegment);
    }

    public void addSegmentToBody(Segment segment) {
        if (segment != null) {
            body.add(segment);
        }
    }

    protected String indentToLevel(int indentLevel) {
        var sb = new StringBuilder();
        for (var i = 0; i < indentLevel; i++) {
            sb.append(INDENT);
        }
        return sb.toString();
    }

    public String print(int indentLevel, boolean splitFiles) {
        var sb = new StringBuilder();

        // Print intended filename if relevant
        if (splitFiles) {
            // TODO
            throw new UnsupportedOperationException();
        } else if (printInOwnFile) {
            sb.append(indentToLevel(indentLevel));
            sb.append("-- splits to own file: ");
            sb.append(fileName);
            sb.append(NEWLINE);
        }

        // Print head
        sb.append(indentToLevel(indentLevel));
        sb.append(head);

        // Append body
        if (body.stream().allMatch(Segment::canBePrintedInlineWithPreviousSegment)) {
            for (var item : body) {
                sb.append(item.print(0, splitFiles));
            }
        } else {
            for (var item : body) {
                sb.append(NEWLINE);
                sb.append(item.print(indentLevel + 1, splitFiles));
            }

            // Prepare to print tail
            sb.append(NEWLINE);
            sb.append(indentToLevel(indentLevel));
        }

        // Append tail
        sb.append(tail);

        return sb.toString();
    }

    @Override
    public String toString() {
        return print(0, false);
    }
}
