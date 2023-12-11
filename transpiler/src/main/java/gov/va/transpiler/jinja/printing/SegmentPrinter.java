package gov.va.transpiler.jinja.printing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import gov.va.transpiler.jinja.node.TranspilerNode.PrintType;
import gov.va.transpiler.jinja.node.ary.LibraryNode;
import gov.va.transpiler.jinja.standards.Standards;

public class SegmentPrinter {

    private static final boolean PRINT_LOCATORS = true;
    private final CQLFileContentRetriever contentRetriever;

    public SegmentPrinter(CQLFileContentRetriever contentRetriever) {
        this.contentRetriever = contentRetriever;
    }

    protected String indentToLevel(int indentLevel) {
        var sb = new StringBuilder();
        for (var i = 0; i < indentLevel; i++) {
            sb.append(Standards.INDENT);
        }
        return sb.toString();
    }

    public void toFiles(Segment segment, String targetPath) throws IOException {
        toFiles(segment, targetPath, 0);
    }

    public void toFiles(Segment segment, String targetPath, int indentLevel) throws IOException {
        var node = segment.getOrigin();
        var path = targetPath + node.getRelativeFilePath();
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

                    // Print the original CQL text
                    if (PRINT_LOCATORS && segment.getLocator() != null) {
                        var current = node;
                        do {
                            current = current.getParent();
                        } while (!(current == null || current instanceof LibraryNode));

                        if (current instanceof LibraryNode) {
                            try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
                                outputStream.write(("/* " + ((LibraryNode) current).getReferenceName() + " lines [" + segment.getLocator() + "]").getBytes());
                                outputStream.write(Standards.NEWLINE.getBytes());
                                var linesFromFile = contentRetriever.getTextFromLibrary(((LibraryNode) current).getCqlEquivalent().getIdentifier(), Locator.fromString(segment.getLocator()));
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

            // recursively print this item's children as files
            for (var item : segment.getBody()) {
                toFiles(item, targetPath);
            }
        } else {
            // Print this segment's head
            try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
                outputStream.write(indentToLevel(indentLevel).getBytes());
                if (segment.printsInline()) {
                    outputStream.write(segment.getHead().getBytes());
                } else {
                    outputStream.write(Standards.NEWLINE.getBytes());
                    outputStream.write(segment.getHead().getBytes());
                }
            }

            // recursively print this item's children as files
            for (var item : segment.getBody()) {
                toFiles(item, targetPath, segment.printsInline() ? 0 : indentLevel + 1);
            }

            // Print this segment's tail
            try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
                if (segment.printsInline()) {
                    outputStream.write(segment.getTail().getBytes());
                } else {
                    outputStream.write(Standards.NEWLINE.getBytes());
                    outputStream.write(indentToLevel(indentLevel).getBytes());
                    outputStream.write(segment.getTail().getBytes());
                }
            }
        }
    }
}
