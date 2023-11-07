package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.Retrieve;

public class RetrieveNode extends AbstractNodeNoChildren<Retrieve> {

    @Override
    public String asOneLine() {
        return "SELECT * FROM " + getName();
    }

    @Override
    public boolean isTable() {
        return true;
    }
}
