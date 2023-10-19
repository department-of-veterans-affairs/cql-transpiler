package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.bulk.sparksql.utilities.Standards;
import gov.va.transpiler.node.OutputNode;
import gov.va.transpiler.node.ParentNode;

public class ListNode extends ParentNode {

    @Override
    public String asOneLine() {
        String unionStatement;
        if (getChildren().size() == 0) {
            unionStatement = Standards.EMPTY_TABLE;
        } else if (getChildren().size() == 1) {
            unionStatement = getChildren().get(0).asOneLine();
        } else {
            boolean first = true;
            unionStatement = "(" + getChildren().get(0).asOneLine() + ")";
            for (int i = 1; i < getChildren().size(); i++) {
                unionStatement += " UNION ALL (" + getChildren().get(i).asOneLine() + ")";
            }
        }
        return "SELECT collect_list(" + Standards.SINGLE_VALUE_COLUMN_NAME + ") FROM (" + unionStatement + ")";
    }
}
