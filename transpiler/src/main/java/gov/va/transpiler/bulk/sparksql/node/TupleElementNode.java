package gov.va.transpiler.bulk.sparksql.node;

public class TupleElementNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return "(" + childAsOneLineCompressedIfTable((AbstractCQLNode) getChild()) + ") " + getName();
    }    
}
