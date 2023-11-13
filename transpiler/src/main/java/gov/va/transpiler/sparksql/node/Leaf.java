package gov.va.transpiler.sparksql.node;

public abstract class Leaf extends AbstractCQLNode {

    @Override
    public boolean addChild(AbstractCQLNode child) {
        return false;
    }
}
