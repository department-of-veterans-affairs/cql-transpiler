package gov.va.transpiler.sparksql.node.ary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.TupleElement;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Ary;
import gov.va.transpiler.sparksql.utilities.Standards;

public class TupleNode extends Ary {

    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (child.getCqlNodeEquivalent() instanceof TupleElement) {
            return super.addChild(child);
        }
        return false;
    }

    @Override
    public String asOneLine() {
        if (!getChildren().isEmpty()) {
            String builder = "SELECT struct(";
            boolean first = true;
            for (AbstractCQLNode child : getChildren()) {
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

    @Override
    public boolean isEncapsulated() {
        return true;
    }
}
