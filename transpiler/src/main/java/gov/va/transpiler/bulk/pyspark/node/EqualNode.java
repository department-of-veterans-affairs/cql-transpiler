package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.node.ParentNode;

public class EqualNode extends ParentNode {

    @Override
    public String asOneLine() {
        if (getChildren().size() != 2 || getChildren().get(0).asOneLine() == null || getChildren().get(1).asOneLine() == null) {
            return null;
        }
        return getChildren().get(0).asOneLine() + " == " + getChildren().get(1).asOneLine();
    }
}
