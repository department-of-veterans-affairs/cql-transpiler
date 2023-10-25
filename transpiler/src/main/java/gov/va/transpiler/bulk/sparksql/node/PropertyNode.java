package gov.va.transpiler.bulk.sparksql.node;

public class PropertyNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return "SELECT _val." + getName() + " FROM (" + childAsOneLineDecompressedIfTable(getChild()) + ")";
    }
}
