package gov.va.transpiler.sparksql.node.ary;

import org.hl7.elm.r1.If;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Ary;

public class IfNode extends Ary {
    private AbstractCQLNode condition;
    private AbstractCQLNode then;
    private AbstractCQLNode otherwise;

    @Override
    public boolean addChild(AbstractCQLNode child) {
        If equivalent = (If) getCqlNodeEquivalent();
        if (equivalent.getCondition() == child.getCqlNodeEquivalent()) {
            condition = child;
            return true;
        } else if (equivalent.getThen() == child.getCqlNodeEquivalent()) {
            then = child;
            return true;
        } else if (equivalent.getElse() == child.getCqlNodeEquivalent()) {
            otherwise = child;
            return true;
        }
        super.addChild(child);
        return false;
    }

    @Override
    public String asOneLine() {
        return "IF (" + condition.asOneLine() + ", " + then.asOneLine() + ", " + otherwise.asOneLine() + ")";
    }    
}
