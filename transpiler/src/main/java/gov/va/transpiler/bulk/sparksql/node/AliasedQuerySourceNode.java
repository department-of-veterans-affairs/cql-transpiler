package gov.va.transpiler.bulk.sparksql.node;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.AliasedQuerySource;

import gov.va.transpiler.node.OutputNode;

public class AliasedQuerySourceNode extends AbstractNodeOneChild<AliasedQuerySource> {

    @Override
    public boolean addChild(OutputNode<? extends Trackable> child) {
        if (child instanceof RetrieveNode) {
            return super.addChild(child);
        }
        return false;
    }

    @Override
    public String asOneLine() {
        return ((RetrieveNode) getChild()).asOneLineWithAlias(getName());
    }
}
