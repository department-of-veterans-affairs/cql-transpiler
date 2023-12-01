package gov.va.transpiler.sparksql.node.ary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import gov.va.transpiler.sparksql.node.Ary;
import gov.va.transpiler.sparksql.utilities.Standards;

public class ListNode extends Ary {

    @Override
    public String asOneLine() {
        // TODO: unify with printing inside UnionNode
        String unionStatement;
        if (getChildren().size() == 0) {
            unionStatement = Standards.EMPTY_TABLE;
        } else {
            var child = getChildren().get(0);
            if (child.isEncapsulated()) {
                unionStatement = child.asOneLine();
            } else if (child.isTable()) {
                unionStatement = childAsOneLineCompressedIfTable(child);
            } else {
                unionStatement = "SELECT " + child.asOneLine() + " " + SINGLE_VALUE_COLUMN_NAME;
            }

            if (getChildren().size() > 1) {
                unionStatement = "(" + unionStatement + ")";
                for (int i = 1; i < getChildren().size(); i++) {
                    child = getChildren().get(i);
                    String unionStatementPiece;
                    if (child.isEncapsulated()) {
                        unionStatementPiece = child.asOneLine();
                    } else if (child.isTable()) {
                        unionStatementPiece = childAsOneLineCompressedIfTable(child);
                    } else {
                        unionStatementPiece = "SELECT " + child.asOneLine() + " " + SINGLE_VALUE_COLUMN_NAME;
                    }
                    unionStatement += " UNION (" + unionStatementPiece + ")";
                }
            }
        }
        return "SELECT collect_list(" + Standards.SINGLE_VALUE_COLUMN_NAME + ") AS " + Standards.SINGLE_VALUE_COLUMN_NAME + " FROM (" + unionStatement + ")";
    }

    @Override
    public boolean isEncapsulated() {
        return true;
    }
}
