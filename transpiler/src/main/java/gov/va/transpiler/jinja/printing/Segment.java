package gov.va.transpiler.jinja.printing;

import java.util.ArrayList;
import java.util.List;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.TranspilerNode.PrintType;

public class Segment {

    private final List<Segment> body;
    private final TranspilerNode origin;
    private String locator;
    private String head = "";
    private String tail = "";

    public Segment(TranspilerNode origin) {
        this.origin = origin;
        body = new ArrayList<>();
    }

    protected boolean printsInline() {
        return (getOrigin().getPrintType()) == PrintType.Inline && body.stream().allMatch(Segment::printsInline);
    }

    public List<Segment> getBody() {
        return body;
    }

    public void addSegmentToBody(Segment segment) {
        if (segment != null) {
            body.add(segment);
        }
    }

    public TranspilerNode getOrigin() {
        return origin;
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

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }
}
