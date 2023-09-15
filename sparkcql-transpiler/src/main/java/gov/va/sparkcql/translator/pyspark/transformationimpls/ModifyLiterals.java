package gov.va.sparkcql.translator.pyspark.transformationimpls;

import org.hl7.elm.r1.Literal;

import gov.va.sparkcql.translator.pyspark.PySparkTransformation;

public class ModifyLiterals extends PySparkTransformation<Literal>{

    @Override
    public Class<Literal> transformsClass() {
        return Literal.class;
    }

    @Override
    public boolean appliesToNode(org.hl7.elm.r1.Element node, org.hl7.elm.r1.Element parentNode) {
        return transformsClass().isInstance(node) && (((Literal) node).getValue().length() < 10);
    }

    @Override
    public int transform(org.hl7.elm.r1.Element node, org.hl7.elm.r1.Element parentNode) {
        if (appliesToNode(node, parentNode)) {
            Literal currentNode = (Literal) node;
            currentNode.setValue(currentNode.getValue() + "!");
            return 1;
        }
        return 0;
    }
    
}
