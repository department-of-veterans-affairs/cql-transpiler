package gov.va.transpiler.jinja.printing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.TranspilerNode.PrintType;
import gov.va.transpiler.jinja.node.ary.LibraryNode;
import gov.va.transpiler.jinja.standards.Standards;

public class Segment {

    private static final boolean PRINT_LOCATORS = true;

    private TranspilerNode fromNode;
    private String locator;
    private String head = "";
    private List<Segment> body = new ArrayList<>();
    private String tail = "";

    public Segment(TranspilerNode fromNode) {
        this.fromNode = fromNode;
    }

    public TranspilerNode getFromNode() {
        return fromNode;
    }

    public void setLocator(String locator) {
        this.locator = locator;
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

    public void toFiles(String basePath) throws IOException {
        toFiles(basePath, 0);
    }

    public void toFiles(String basePath, int indentLevel) throws IOException {
        var node = getFromNode();
        var path = basePath + node.getRelativeFilePath();
        File file = new File(path);

        // Print this file's contents
        if (node.getPrintType() == PrintType.Folder || node.getPrintType() == PrintType.File) {
            if (file.exists()) {
                throw new IOException("File already exists at path: " + path);
            } else {
                if (node.getPrintType() == PrintType.Folder) {
                    file.mkdir();
                } else if (node.getPrintType() == PrintType.File) {
                    file.createNewFile();

                    if (PRINT_LOCATORS && locator != null) {
                        var current = node;
                        do {
                            current = current.getParent();
                        } while (!(current == null || current instanceof LibraryNode));

                        if (current instanceof LibraryNode) {
                            try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
                                outputStream.write(("/* " + ((LibraryNode) current).getReferenceName() + " lines [" + locator + "]").getBytes());
                                outputStream.write(Standards.NEWLINE.getBytes());
                                var linesFromFile = Locator.fromString(locator).getLinesFromFile(((LibraryNode) current).getAbsolutePathToLibrary());
                                for (var line : linesFromFile) {
                                    outputStream.write(line.getBytes());
                                    outputStream.write(Standards.NEWLINE.getBytes());
                                }
                                outputStream.write("*/".getBytes());
                                outputStream.write(Standards.NEWLINE.getBytes());
                            }
                        }
                    }
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
