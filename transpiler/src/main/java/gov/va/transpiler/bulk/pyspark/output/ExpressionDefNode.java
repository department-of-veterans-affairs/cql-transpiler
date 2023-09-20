package gov.va.transpiler.bulk.pyspark.output;

import java.util.ArrayList;
import java.util.List;

import gov.va.transpiler.output.OutputNode;

public class ExpressionDefNode extends OutputNode {

    private String name;
    private OutputNode child;
    @SuppressWarnings("unused")
    /** we don't support access modifiers */
    private AccessModifierNode accessModifier;
    private List<OutputNode> otherChildren = new ArrayList<>();

    @Override
    public void addChild(OutputNode child) {
        if (child instanceof LiteralNode || child instanceof ExpressionRefNode) {
            this.child = child;
        } else if (child instanceof AccessModifierNode){
            accessModifier = (AccessModifierNode) child;
        } else {
            otherChildren.add(child);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String asOneLine() {
        String builder = "";
        if (child == null) {
            builder += "# no valid child for [ " + name + " ]";
        } else {
            // We don't support access modifiers
            // builder += accessModifier.asOneLine() + " " + name + " = " + child.asOneLine();
            builder += name + " = " + child.asOneLine();
        }
        if (!otherChildren.isEmpty()) {
            builder += " # Unsupported children of [" + name + "] : [";
            for (OutputNode child : otherChildren) {
                builder += " " + child.asOneLine();
            }
            builder += " ]";
        }
        return builder;
    }
}
