package gov.va.transpiler.jinja.node;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.standards.Standards;

public interface TranspilerNode {

    public enum PrintType {
        Folder,
        File,
        Line,
        Inline
    }

    /**
     * @param parent This node's parent, to allow transversal.
     */
    public void setParent(TranspilerNode parent);

    /**
     * @preturn This node's parent.
     */
    public TranspilerNode getParent();

    /**
     * @param child Child to add to this node.
     * @return True if the child was added. False otherwise.
     */
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException;

    /**
     * @return A segment to use to print this node.
     */
    public Segment toSegment();

    /**
     * @return whether this represents a table value
     */
    public boolean isTable();

    /**
     * @return whether this represents a simple value
     */
    public boolean isSimpleValue();

    /**
     * @return How this node should be printed overall.
     */
    public PrintType getPrintType();

    /**
     * @return The name this node can be referenced by. Null if none exists.
     */
    public default String getReferenceName() {
        return null;
    }

    /**
     * @return Where this node is located.
     */
    public default String getRelativeFilePath() {
        var sb = new StringBuilder();
        if (getParent() != null) {
            sb.append(getParent().getRelativeFilePath());
        }
        if (getPrintType() == PrintType.Folder) {
            sb.append(getReferenceName());
            sb.append(Standards.FOLDER_SEPARATOR);
        } else if (getPrintType() == PrintType.File) {
            // Files are never parents of other files
            sb.append(getReferenceName());
        }
        return sb.toString();
    }
}
