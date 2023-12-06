package gov.va.transpiler.jinja.printing;

import java.util.ArrayList;
import java.util.List;

public class Segment {
    private static final String INDENT = "\t";
    private static final String NEWLINE = "\n";
    private static final String FOLDER_SEPARATOR = "/";

    public enum PrintType {
        Unset,
        Folder,
        File,
        Line,
        Inline
    }

    private PrintType printType = PrintType.Unset;
    private String head = "";
    private List<Segment> body = new ArrayList<>();
    private String tail = "";
    private String name = null;

    public PrintType getPrintType() {
        return printType;
    }

    public void setPrintType(PrintType printType) {
        this.printType = printType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            sb.append(INDENT);
        }
        return sb.toString();
    }

    protected boolean printInline() {
        return getPrintType() == PrintType.Inline && body.stream().allMatch(Segment::printInline);
    }

    public String print(int indentLevel, boolean splitFiles) {
        if (getPrintType() == PrintType.Unset) {
            throw new RuntimeException("Nodes must have print type set");
        }
        var sb = new StringBuilder();

        if ((getPrintType() == PrintType.File) || (getPrintType() == PrintType.Folder)) {
            if (splitFiles) {
                // TODO
                throw new UnsupportedOperationException();
            } else {
                sb.append(indentToLevel(indentLevel));
                if (getPrintType() == PrintType.File) {
                    sb.append("-- splits to own file: ");
                } else if (getPrintType() == PrintType.Folder) {
                    sb.append(indentToLevel(indentLevel));
                    sb.append("-- splits to own folder: ");
                }
                sb.append(getName());
                sb.append(FOLDER_SEPARATOR);
                sb.append(NEWLINE);
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
                sb.append(NEWLINE);
                sb.append(item.print(indentLevel + 1, splitFiles));
            }
            sb.append(NEWLINE);
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
