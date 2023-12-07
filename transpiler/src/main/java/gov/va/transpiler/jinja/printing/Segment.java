package gov.va.transpiler.jinja.printing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    protected boolean printsInline() {
        return (getFromNode().getPrintType()) == PrintType.Inline && body.stream().allMatch(Segment::printsInline);
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int indentLevel) {
        var sb = new StringBuilder();

        if (getFromNode().getPrintType() == PrintType.Folder || getFromNode().getPrintType() == PrintType.File) {
            sb.append(indentToLevel(indentLevel));
            sb.append("-- splits to own ");
            sb.append(getFromNode().getPrintType());
            sb.append(":");
            sb.append(getFromNode().getRelativeFilePath());
        }

        if (printsInline()) {
                sb.append(indentToLevel(indentLevel));
                sb.append(head);
                for (var item : body) {
                    sb.append(item.toString(0));
                }
                sb.append(tail);
        } else {
            sb.append(indentToLevel(indentLevel));
            sb.append(head);
            for (var item : body) {
                sb.append(Standards.NEWLINE);
                sb.append(item.toString(indentLevel + 1));
            }
            sb.append(Standards.NEWLINE);
            sb.append(indentToLevel(indentLevel));
            sb.append(tail);
        }

        return sb.toString();
    }

    public void toFiles(String basePath) throws IOException {
        toFiles(basePath, 0);
    }

    public void toFiles(String basePath, int indentLevel) throws IOException {
        var path = basePath + getFromNode().getRelativeFilePath();
        File file = new File(path);

        // Print this file's contents
        if (getFromNode().getPrintType() == PrintType.Folder || getFromNode().getPrintType() == PrintType.File) {
            if (file.exists()) {
                throw new IOException("File already exists at path: " + path);
            } else {
                if (getFromNode().getPrintType() == PrintType.Folder) {
                    file.mkdir();
                } else if (getFromNode().getPrintType() == PrintType.File) {
                    file.createNewFile();
                }
            }
            for (var item : body) {
                item.toFiles(basePath);
            }
        } else {
            try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
                outputStream.write(indentToLevel(indentLevel).getBytes());
                if (printsInline()) {
                    outputStream.write(head.getBytes());
                } else {
                    outputStream.write(Standards.NEWLINE.getBytes());
                    outputStream.write(head.getBytes());
                }
            }

            for (var item : body) {
                item.toFiles(basePath, printsInline() ? 0 : indentLevel + 1);
            }

            try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
                if (printsInline()) {
                    outputStream.write(tail.getBytes());
                } else {
                    outputStream.write(Standards.NEWLINE.getBytes());
                    outputStream.write(indentToLevel(indentLevel).getBytes());
                    outputStream.write(tail.getBytes());
                }
            }
        }
    }
}
