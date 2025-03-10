package gov.va.transpiler.jinja.printing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.standards.Standards;

/**
 * Turns a {@link Segment} tree into Jinja files.
 */
public class SegmentPrinter {

    /**
     * Whether to print the original CQL a particular string of Jinja is derived from
     */
    private static final boolean PRINT_LOCATORS = true;
    private final CQLFileContentRetriever contentRetriever;

    /**
     * @param contentRetriever Used to retrieve original CQL.
     */
    public SegmentPrinter(CQLFileContentRetriever contentRetriever) {
        this.contentRetriever = contentRetriever;
    }

    /**
     * Prints a segment tree rooted at a given segment into the filesystem.
     * 
     * @param segment Root segment.
     * @param targetPath File system location to print segment to.
     * @throws IOException
     */
    public void toFiles(Segment segment, String targetPath) throws IOException {
        // Folder and File segments require new files to be created. Line and Inline segments are printed inside existing file.s
        switch (segment.getPrintType()) {
            case Folder:
                folderSegmentToFiles(segment, targetPath);
                break;
            case File:
                fileSegmentToFile(segment, targetPath);
                break;
            case Line:
            case Inline:
                printTextSegment(segment, targetPath);
                break;
            default:
                throw new IOException("Unexpected Segment Type");
        }
    }


    /**
     * Creates a folder equivalent to the root segment, and then prints its children inside of the created folder.
     * 
     * @param segment Root segment.
     * @param targetPath File system location to print segment to.
     * @throws IOException
     */
    protected void folderSegmentToFiles(Segment segment, String targetPath) throws IOException {
        var path = targetPath + segment.getFileLocation();
        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }

        file.mkdir();

        for (var child : segment.getChildren()) {
            toFiles(child, targetPath);
        }
    }

    /**
     * Creates a file equivalent to the root segment, and then prints its children inside of the created file.
     * 
     * @param segment Root segment.
     * @param targetPath File system location to print segment to.
     * @throws IOException
     */
    protected void fileSegmentToFile(Segment segment, String targetPath) throws IOException {
        var path = targetPath + segment.getFileLocation() + Standards.JINJA_FILE_POSTFIX;
        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }

        file.createNewFile();

        for (var child : segment.getChildren()) {
            toFiles(child, targetPath);
        }
    }

    /**
     * Prints a text segment and its children inside a file.
     * 
     * @param segment Root segment.
     * @param targetPath Parent path to print root segment to.
     * @throws FileNotFoundException
     * @throws IOException
     */
    protected void printTextSegment(Segment segment, String targetPath) throws FileNotFoundException, IOException {
        var path = targetPath + segment.getFileLocation() + Standards.JINJA_FILE_POSTFIX;
        File file = new File(path);

        // Print the original CQL text if PRINT_LOCATORS is enabled and locators are specified
        if (PRINT_LOCATORS && segment.getLocator() != null) {
            printOriginalCQLToFile(file, segment);
        }

        // Print this segment's head
        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            outputStream.write(segment.getHead().getBytes());
        }

        // recursively print this item's children
        for (var item : segment.getChildren()) {
            toFiles(item, targetPath);
        }

        // Print this segment's tail
        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            outputStream.write(segment.getTail().getBytes());
            if (segment.getPrintType() == PrintType.Line) {
                outputStream.write(Standards.NEWLINE.getBytes());
            }
        }
    }

    /**
     * Uses the {@link CQLFileContentRetriever} to print original CQL text to a file as an SQL comment.
     * @param file File to print to.
     * @param segment {@link Segment} to print {@link Locator} for.
     * @throws IOException
     */
    protected void printOriginalCQLToFile(File file, Segment segment) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            // Begin SQL comment
            outputStream.write("/*" .getBytes());
            outputStream.write(Standards.NEWLINE.getBytes());

            // Print locator location
            outputStream.write((Standards.INDENT + "// " + segment.getOriginalLibraryIdentifier().getId() + " lines [" + segment.getLocator() + "]").getBytes());
            var linesFromFile = contentRetriever.getLinesOfTextFromLibrary(segment.getOriginalLibraryIdentifier(), Locator.fromString(segment.getLocator() ));

            // Print lines from original CQL file
            for (var line : linesFromFile) {
                outputStream.write(Standards.NEWLINE.getBytes());
                outputStream.write(Standards.INDENT.getBytes());
                outputStream.write(line.getBytes());
            }
            outputStream.write(Standards.NEWLINE.getBytes());

            // End comment
            outputStream.write("*/".getBytes());
            outputStream.write(Standards.NEWLINE.getBytes());
        }
    }
}
