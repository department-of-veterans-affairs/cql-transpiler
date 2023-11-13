package gov.va.transpiler.sparksql.node.ary;

import gov.va.transpiler.sparksql.node.AbstractNodeWithChildren;
import gov.va.transpiler.sparksql.utilities.Standards;

public class ListNode extends AbstractNodeWithChildren {

    @Override
    public String asOneLine() {
        String unionStatement;
        if (getChildren().size() == 0) {
            unionStatement = Standards.EMPTY_TABLE;
        } else if (getChildren().size() == 1) {
            unionStatement = childAsOneLineCompressedIfTable(getChildren().get(0));
        } else {
            unionStatement = "(" + childAsOneLineCompressedIfTable(getChildren().get(0)) + ")";
            for (int i = 1; i < getChildren().size(); i++) {
                unionStatement += " UNION ALL (" + childAsOneLineCompressedIfTable(getChildren().get(i)) + ")";
            }
        }
        return "SELECT collect_list(" + Standards.SINGLE_VALUE_COLUMN_NAME + ") AS " + Standards.SINGLE_VALUE_COLUMN_NAME + " FROM (" + unionStatement + ")";
    }
}
