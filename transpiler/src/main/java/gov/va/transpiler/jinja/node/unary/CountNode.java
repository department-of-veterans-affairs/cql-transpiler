
package gov.va.transpiler.jinja.node.unary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.Count;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class CountNode extends Unary<Count> {

    private String context;

    public CountNode(State state, Count t) {
        super(state, t);
        setContext(state.getContext());
    }

    public String getContext() {
        return context;
    }

    protected void setContext(String context) {
        this.context = context;
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        if (getChild().isTable()) {
            segment.setHead("SELECT count(*) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (");
            segment.addChild(getChild().toSegment());
            segment.setTail(")" + (getContext() == null ? "" : " GROUP BY " + contextToParam(getContext())));
        } else {
            segment.setHead("SELECT count(explod(*)) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (");
            segment.addChild(getChild().toSegment());
            segment.setTail(") AS " + SINGLE_VALUE_COLUMN_NAME);
        }
        return segment;
    }

    @Override
    public boolean isTable() {
        return getChild().isTable() && getContext() != null;
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }
}
