package gov.va.transpiler.jinja.printing;

/**
 * Thrown on attempts to add an invalid child to a segment. If you see this exception, there is a problem with the printing logic.
 */
public class UnsupportedChildSegmentException extends RuntimeException {

    private Segment parent;
    private Segment child;

    public UnsupportedChildSegmentException(Segment parent, Segment child) {
        this.parent = parent;
        this.child = child;
    }

    @Override
    public String toString() {
        return "Parent Segment ["  + parent + "] does not support child [" + child + "].";
    }
}
