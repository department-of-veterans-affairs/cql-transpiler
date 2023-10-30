package gov.va.transpiler.bulk.sparksql.node;

// Child is always a period
public class EndNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return getChild().asOneLine() + ".end";
    }
    
}
