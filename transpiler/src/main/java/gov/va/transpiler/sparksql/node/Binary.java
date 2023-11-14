package gov.va.transpiler.sparksql.node;

public abstract class Binary extends AbstractCQLNode {

    private AbstractCQLNode child1;
    private AbstractCQLNode child2;

    public AbstractCQLNode getChild1() {
        return child1;
    }

    protected void setChild1(AbstractCQLNode child1) {
        this.child1 = child1;
    }

    public AbstractCQLNode getChild2() {
        return child2;
    }

    protected void setChild2(AbstractCQLNode child2) {
        this.child2 = child2;
    }

    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (child instanceof AbstractCQLNode) {
            if (getChild1() == null) {
                setChild1((AbstractCQLNode) child);
                return true;
            } else if (getChild2() == null) {
                setChild2((AbstractCQLNode) child);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isEncapsulated() {
        return getChild1().isEncapsulated() || getChild2().isEncapsulated();
    }
}
