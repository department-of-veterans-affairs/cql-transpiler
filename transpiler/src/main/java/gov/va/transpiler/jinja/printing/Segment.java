package gov.va.transpiler.jinja.printing;

import java.util.ArrayList;
import java.util.List;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.TranspilerNode.PrintType;
import gov.va.transpiler.jinja.standards.Standards;

public class Segment {

    private TranspilerNode fromNode;
    private String head = "";
    private List<Segment> body = new ArrayList<>();
    private String tail = "";

    public Segment(TranspilerNode fromNode) {
        this.fromNode = fromNode;
    }

    public TranspilerNode getFromNode() {
        return fromNode;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public void addSegmentToBody(Segment segment) {
        if (segment != null) {
            body.add(segment);
        }
    }

    protected String indentToLevel(int indentLevel) {
        var sb = new StringBuilder();
        for (var i = 0; i < indentLevel; i++) {
            sb.append(Standards.INDENT);
        }
        return sb.toString();
    }

    protected boolean printInline() {
        return (getFromNode().getPrintType()) == PrintType.Inline && body.stream().allMatch(Segment::printInline);
    }

    public String print(int indentLevel, boolean splitFiles) {
        var sb = new StringBuilder();

        if ((getFromNode().getPrintType() == PrintType.File) || (getFromNode().getPrintType() == PrintType.Folder)) {
            if (splitFiles) {
                // TODO
                throw new UnsupportedOperationException();
            } else if (getFromNode().getPrintType() == PrintType.Folder || getFromNode().getPrintType() == PrintType.File) {
                sb.append(indentToLevel(indentLevel));
                sb.append("-- splits to own ");
                sb.append(getFromNode().getPrintType());
                sb.append(":");
                sb.append(getFromNode().getRelativeFilePath());
            }
        }

        if (printInline()) {
                sb.append(indentToLevel(indentLevel));
                sb.append(head);
                for (var item : body) {
                    sb.append(item.print(0, splitFiles));
                }
                sb.append(tail);
        } else {
            sb.append(indentToLevel(indentLevel));
            sb.append(head);
            for (var item : body) {
                sb.append(Standards.NEWLINE);
                sb.append(item.print(indentLevel + 1, splitFiles));
            }
            sb.append(Standards.NEWLINE);
            sb.append(indentToLevel(indentLevel));
            sb.append(tail);
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return print(0, false);
    }
}
