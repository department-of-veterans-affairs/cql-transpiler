package gov.va.transpiler.bulk.sparksql.node;

public class TupleElementNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return "SELECT * FROM (" + childAsOneLineCompressedIfTable((AbstractCQLNode) getChild()) + ") AS " + getName();
    }    
}
