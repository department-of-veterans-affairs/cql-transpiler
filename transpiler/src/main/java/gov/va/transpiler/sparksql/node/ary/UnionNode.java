package gov.va.transpiler.sparksql.node.ary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Ary;

/**
 * If a simple list is unioned with another simple list, the outcome is a simple list. Otherwise the outcome is a table.
 */
public class UnionNode extends Ary {

    @Override
    public String asOneLine() {
        // TODO: unify with printing inside ListNode
        var child = getChildren().get(0);
        String unionStatement;
        if (isEncapsulated()) {
            unionStatement = "(SELECT explode(*) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + child.asOneLine() + "))";
            // Unions have a minimum of two children
            for (int i = 1; i < getChildren().size(); i++) {
                child = getChildren().get(i);
                unionStatement += " UNION (SELECT explode(*) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + child.asOneLine() + "))";
            }
            return "SELECT collect_list(*) FROM " + unionStatement;
        } else {
            unionStatement = child.isEncapsulated() ? "(SELECT explode(*) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + child.asOneLine() + "))" : "(" + child.asOneLine() + ")";
            // Unions have a minimum of two children
            for (int i = 1; i < getChildren().size(); i++) {
                child = getChildren().get(i);
                unionStatement += " UNION ";
                unionStatement += child.isEncapsulated() ? "(SELECT * FROM (" + child.asOneLine() + "))" : "(" + child.asOneLine() + ")";
            }
            return unionStatement;
        }
    }

    @Override
    public boolean isEncapsulated() {
        return getChildren().stream().allMatch(AbstractCQLNode::isEncapsulated);
    }

    @Override
    public boolean isTable() {
        return getChildren().stream().anyMatch(AbstractCQLNode::isTable);
    }
}
