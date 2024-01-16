package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.Unary;

public class ToDateNode extends Unary {

    @Override
    public String asOneLine() {
        return getChild().isEncapsulated() ? "CAST ((" + getChild().asOneLine() + ") AS DATE)" :  "CAST (" + getChild().asOneLine() + " AS DATE)";
    }    
}
