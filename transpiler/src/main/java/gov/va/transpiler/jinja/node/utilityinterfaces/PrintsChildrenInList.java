package gov.va.transpiler.jinja.node.utilityinterfaces;

import java.util.List;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;

public interface PrintsChildrenInList {
    

    public default Segment childToSegment(TranspilerNode child) {
        return child.toSegment();
    }

    public default Segment toSegmentWithJoinedChildren(List<TranspilerNode> children, String head, String tail, String childPrefix, String childPostfix, String childJoinerInline) {
        var topLevel = new Segment();
        topLevel.setHead(head);
        switch(children.size()) {
            case 0:
                break;
            case 1:
                topLevel.addChild(childToSegment(children.get(0)));
                break;
            default:
                for (int i = 0; i < children.size(); i++) {
                    // Prefix
                    var prefixSegment = new Segment();
                    prefixSegment.setHead(childPrefix);
                    topLevel.addChild(prefixSegment);

                    // Child
                    topLevel.addChild(childToSegment(children.get(i)));

                    // Postfix
                    var postfixSegment = new Segment();
                    boolean last = i == children.size() - 1;
                    postfixSegment.setHead(last ? childPostfix : childPostfix + (childJoinerInline));
                    topLevel.addChild(postfixSegment);
                }
                break;
        }
        topLevel.setTail(tail);
        return topLevel;
    }
}
