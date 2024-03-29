package gov.va.transpiler.sparksql.node;

public abstract class Unary extends Ary {

    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (getChildren().isEmpty()) {
            return super.addChild(child);
        }
        return false;
    }

    public boolean hasChild() {
        return !getChildren().isEmpty();
    }

    public AbstractCQLNode getChild() {
        return getChildren().get(0);
    }

    @Override
    public boolean isTable() {
        return hasChild() && getChild().isTable();
    }

    @Override
    public boolean isEncapsulated() {
        return hasChild() && getChild().isEncapsulated();
    }
}
