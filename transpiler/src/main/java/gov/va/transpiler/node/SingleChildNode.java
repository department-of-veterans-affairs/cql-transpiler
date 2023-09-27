package gov.va.transpiler.node;

public abstract class SingleChildNode extends ParentNode {

    @Override
    public boolean addChild(OutputNode child) {
        if (getChildren().isEmpty()) {
            return super.addChild(child);
        }
        return false;
    }
}
