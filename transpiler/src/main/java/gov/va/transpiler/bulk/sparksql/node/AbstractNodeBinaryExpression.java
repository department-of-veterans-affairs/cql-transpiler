package gov.va.transpiler.bulk.sparksql.node;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.OperatorExpression;

import gov.va.transpiler.node.OutputNode;

public abstract class AbstractNodeBinaryExpression<T extends OperatorExpression> extends AbstractCQLNode<T> {

    private AbstractCQLNode<? extends Trackable> child1;
    private AbstractCQLNode<? extends Trackable> child2;

    public AbstractCQLNode<? extends Trackable> getChild1() {
        return child1;
    }

    protected void setChild1(AbstractCQLNode<? extends Trackable> child1) {
        this.child1 = child1;
    }

    public AbstractCQLNode<? extends Trackable> getChild2() {
        return child2;
    }

    protected void setChild2(AbstractCQLNode<? extends Trackable> child2) {
        this.child2 = child2;
    }

    @Override
    public boolean addChild(OutputNode<? extends Trackable> child) {
        if (child instanceof AbstractCQLNode) {
            if (getChild1() == null) {
                setChild1((AbstractCQLNode<? extends Trackable>) child);
                return true;
            } else if (getChild2() == null) {
                setChild2((AbstractCQLNode<? extends Trackable>) child);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isTable() {
        return false;
    }
}
