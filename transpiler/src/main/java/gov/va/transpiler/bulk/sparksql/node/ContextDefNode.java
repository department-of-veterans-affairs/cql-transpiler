package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.ContextDef;

public class ContextDefNode extends AbstractNodeNoChildren<ContextDef> {

    @Override
    public String asOneLine() {
        return "-- Context: " + getName();
    }
}
