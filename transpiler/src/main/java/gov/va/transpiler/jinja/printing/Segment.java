package gov.va.transpiler.jinja.printing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hl7.elm.r1.VersionedIdentifier;

public class Segment {

    public enum PrintType {
        Folder,
        File,
        Line,
        Inline
    }

    private Segment parent;
    private PrintType printType;
    private VersionedIdentifier originalLibraryIdentifier;
    private String fileLocation;
    private String locator;
    private String head;
    private final List<Segment> children;
    private String tail;

    public Segment() {
        this("", "", PrintType.Inline);
    }

    public Segment (String head) {
        this(head, "", PrintType.Inline);
    }

    public Segment(String head, String tail, PrintType printType) {
        this.printType = printType;
        this.head = head;
        children = new ArrayList<>();
        this.tail = tail;
    }

    public Segment getParent() {
        return parent;
    }
    public void setParent(Segment parent) {
        this.parent = parent;
    }
    public PrintType getPrintType() {
        return printType;
    }
    public void setPrintType(PrintType printType) {
        this.printType = printType;
    }
    public VersionedIdentifier getOriginalLibraryIdentifier() {
        return originalLibraryIdentifier != null || getParent() == null ? originalLibraryIdentifier : getParent().getOriginalLibraryIdentifier();
    }
    public void setOriginalLibraryIdentifier(VersionedIdentifier originalLibraryIdentifier) {
        this.originalLibraryIdentifier = originalLibraryIdentifier;
    }
    public String getFileLocation() {
        return fileLocation != null || getParent() == null ? fileLocation : getParent().getFileLocation();
    }
    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
    public String getLocator() {
        return locator;
    }
    public void setLocator(String locator) {
        this.locator = locator;
    }
    public String getHead() {
        return head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public void addChild(Segment child) {
        if (child != null) {
            // Folders can only be parents of files and folders 
            if (getPrintType() == PrintType.Folder && !((child.getPrintType() == PrintType.Folder) || (child.getPrintType() == PrintType.File))) {
                throw new UnsupportedChildSegmentException(this, child);
            }
            // Files cannot be parents of files or folders
            if (getPrintType() == PrintType.File && (child.getPrintType() == PrintType.Folder || child.getPrintType() == PrintType.File)) {
                throw new UnsupportedChildSegmentException(this, child);
            }
            child.setParent(this);
            children.add(child);
        }
    }
    public List<Segment> getChildren() {
        return Collections.unmodifiableList(children);
    }
    public String getTail() {
        return tail;
    }
    public void setTail(String tail) {
        this.tail = tail;
    }
}
