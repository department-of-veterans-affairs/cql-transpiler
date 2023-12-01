package gov.va.transpiler.sparksql.node.unary;

import org.hl7.elm.r1.Expression;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Unary;

public class ByExpressionNode extends Unary {

    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (child.getCqlNodeEquivalent() instanceof Expression) {
            return super.addChild(child);
        }
        return false;
    }

    @Override
    public String asOneLine() {
        return getChild().asOneLine();
    }
}
