package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.End;

// Child is always a period
public class EndNode extends AbstractNodeOneChild<End> {

    @Override
    public String asOneLine() {
        return getChild().asOneLine() + ".end";
    }
}
