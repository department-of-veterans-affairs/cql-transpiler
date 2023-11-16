package gov.va.transpiler.sparksql.node.ary;

import gov.va.transpiler.sparksql.node.Ary;

// Spark SQL doesn't support type specifiers
public abstract class TypeSpecifierNode extends Ary {

    @Override
    public String asOneLine() {
        return getName();
    }
}
