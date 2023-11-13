package gov.va.transpiler.sparksql.node;

public abstract class AbstractNodeNoChildren extends AbstractCQLNode {

    @Override
    public boolean addChild(AbstractCQLNode child) {
        return false;
    }
}
