package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.SingletonFrom;

public class SingletonFromNode extends AbstractNodeOneChild<SingletonFrom> {

    @Override
    public String asOneLine() {
        return getChild().asOneLine();
    }
}
