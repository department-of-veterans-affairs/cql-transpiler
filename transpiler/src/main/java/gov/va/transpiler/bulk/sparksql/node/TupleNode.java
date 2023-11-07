package gov.va.transpiler.bulk.sparksql.node;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.Tuple;

import gov.va.transpiler.bulk.sparksql.utilities.Standards;
import gov.va.transpiler.node.OutputNode;

public class TupleNode extends AbstractNodeWithChildren<Tuple> {

    @Override
    public boolean addChild(OutputNode<? extends Trackable> child) {
        if (child instanceof TupleElementNode) {
            return super.addChild(child);
        }
        return false;
    }

    @Override
    public String asOneLine() {
        if (!getChildren().isEmpty()) {
            String builder = "SELECT struct(";
            boolean first = true;
            for (OutputNode<? extends Trackable> child : getChildren()) {
                if (first) {
                    first = false;
                } else {
                    builder += ", ";
                }
                builder += child.asOneLine();
            }
            builder += ") AS _val";
            return builder;
        }
        return Standards.EMPTY_TABLE;
    }
}
