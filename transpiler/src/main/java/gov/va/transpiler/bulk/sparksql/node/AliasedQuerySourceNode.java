package gov.va.transpiler.bulk.sparksql.node;

public class AliasedQuerySourceNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return "(" + getChild().asOneLine() + ") AS " + getName();
    }
}
