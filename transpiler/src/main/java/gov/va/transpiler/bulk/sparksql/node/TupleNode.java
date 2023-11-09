package gov.va.transpiler.bulk.sparksql.node;

import static gov.va.transpiler.bulk.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

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
            builder += ") AS " + SINGLE_VALUE_COLUMN_NAME;
            return builder;
        }
        return Standards.EMPTY_TABLE;
    }
}
