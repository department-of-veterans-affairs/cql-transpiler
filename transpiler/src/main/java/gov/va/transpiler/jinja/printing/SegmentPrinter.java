package gov.va.transpiler.jinja.printing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import gov.va.transpiler.jinja.printing.Segment.PrintType;
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
        switch (segment.getPrintType()) {
            case Folder:
                folderSegmentToFiles(segment, targetPath);
                break;
            case File:
                fileSegmentToFile(segment, targetPath);
                break;
            case Line:
            case Inline:
                toFiles(segment, targetPath, 0);
                break;
            default:
                throw new IOException("Unexpected Segment Type");
        }
    }

    private void folderSegmentToFiles(Segment segment, String targetPath) throws IOException {
        var path = targetPath + segment.getFileLocation();
        File file = new File(path);

        if (file.exists()) {
            throw new IOException("Folder already exists at path: " + path);
        } else {
            file.mkdir();
        }

        for (var child : segment.getChildren()) {
            toFiles(child, targetPath);
        }
    }

    private void fileSegmentToFile(Segment segment, String targetPath) throws IOException {
        var path = targetPath + segment.getFileLocation();
        File file = new File(path);

        if (file.exists()) {
            throw new IOException("File already exists at path: " + path);
        } else {
            file.createNewFile();
        }

        // Print the original CQL text if locators are specified
        if (PRINT_LOCATORS && segment.getLocator() != null) {
            printOriginalCQLToFile(file, segment);
        }

        for (var child : segment.getChildren()) {
            toFiles(child, targetPath);
        }
    }

    private void printOriginalCQLToFile(File file, Segment segment) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            outputStream.write("{% comment %}" .getBytes());
            outputStream.write(Standards.NEWLINE.getBytes());
            outputStream.write((Standards.INDENT + "// " + segment.getOriginalLibraryIdentifier().getId() + " lines [" + segment.getLocator() + "]").getBytes());
            var linesFromFile = contentRetriever.getTextFromLibrary(segment.getOriginalLibraryIdentifier(), Locator.fromString(segment.getLocator() ));
            for (var line : linesFromFile) {
                outputStream.write(Standards.NEWLINE.getBytes());
                outputStream.write(Standards.INDENT.getBytes());
                outputStream.write(line.getBytes());
            }
            outputStream.write(Standards.NEWLINE.getBytes());
            outputStream.write("{% endcomment %}".getBytes());
            outputStream.write(Standards.NEWLINE.getBytes());
        }
    }

    private void toFiles(Segment segment, String targetPath, int indentLevel) throws FileNotFoundException, IOException {
        var path = targetPath + segment.getFileLocation();
        File file = new File(path);

        // Print this segment's head
        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            if (segment.getPrintType() == PrintType.Line) {
                outputStream.write(Standards.NEWLINE.getBytes());
                outputStream.write(indentToLevel(indentLevel).getBytes());
            }
            outputStream.write(segment.getHead().getBytes());
        }

        // recursively print this item's children
        for (var item : segment.getChildren()) {
            toFiles(item, targetPath, segment.getPrintType() == PrintType.Inline ? indentLevel : indentLevel + 1);
        }

        // Print this segment's tail
        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            if (segment.getPrintType() == PrintType.Line) {
                outputStream.write(Standards.NEWLINE.getBytes());
                outputStream.write(indentToLevel(indentLevel).getBytes());
            }
            outputStream.write(segment.getTail().getBytes());
        }
    }
}
