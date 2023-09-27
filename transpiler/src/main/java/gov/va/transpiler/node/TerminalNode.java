package gov.va.transpiler.node;

public abstract class TerminalNode extends OutputNode{

    @Override
    public boolean addChild(OutputNode child) {
        return false;
    }
}
