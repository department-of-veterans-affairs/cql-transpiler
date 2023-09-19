package gov.va.transpiler.bulk.transformations;

import org.hl7.elm.r1.Element;
import org.hl7.elm.r1.Literal;

import gov.va.transpiler.bulk.BulkTransformation;

public class ModifyLiterals extends BulkTransformation<Literal> {

    @Override
    public boolean appliesToNode(Element node, Element parentNode) {
        return node instanceof Literal && (((Literal) node).getValue().length() < 10);
    }

    @Override
    public int transform(Element node, Element parentNode) {
        if (appliesToNode(node, parentNode)) {
            Literal currentNode = (Literal) node;
            currentNode.setValue(currentNode.getValue() + "1");
            return 1;
        }
        return 0;
    }
}