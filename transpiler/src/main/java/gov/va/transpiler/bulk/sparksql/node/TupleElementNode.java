package gov.va.transpiler.bulk.sparksql.node;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.TupleElement;

public class TupleElementNode extends AbstractNodeOneChild<TupleElement> {

    @Override
    public String asOneLine() {
        return "(" + childAsOneLineCompressedIfTable((AbstractCQLNode<? extends Trackable>) getChild()) + ") " + getName();
    }    
}
