package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.Property;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;
import gov.va.transpiler.sparksql.utilities.Standards;

public class PropertyNode extends Unary<Property> {

    public PropertyNode(State state, Property t) {
        super(state, t);
    }

    protected boolean isColumnReference() {
        return  getCqlEquivalent().getScope() != null || (getChild() instanceof PropertyNode && ((PropertyNode) getChild()).isColumnReference());
    }

    protected boolean mustDecompress() {
        return !(isColumnReference() || getChild().isTable());
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment(this);
        if (isColumnReference()) {
            if (getScope() != null) {
                segment.setHead(getCqlEquivalent().getScope() + "." + getCqlEquivalent().getPath());
            } else {
                segment.addSegmentToBody(getChild().toSegment());
                segment.setTail("." + getCqlEquivalent().getPath());
            }
        } else if (getChild().isTable()) {
            // decompress compressed child tables
            segment.setHead("SELECT col.* FROM (SELECT explode(*) FROM (SELECT " + Standards.SINGLE_VALUE_COLUMN_NAME + "." + getCqlEquivalent().getPath() + " FROM (");
            segment.addSegmentToBody(getChild().toSegment());
            segment.setTail(")))");
        } else {
            // Any child that can be referenced by property must be stored as a single-value table with a _val column
            segment.setHead("SELECT " + Standards.SINGLE_VALUE_COLUMN_NAME + "." + getCqlEquivalent().getPath() + " AS " + Standards.SINGLE_VALUE_COLUMN_NAME + " FROM (");
            segment.addSegmentToBody(getChild().toSegment());
            segment.setTail(")");
        }
        return segment;
    }
}
