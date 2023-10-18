package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.node.OutputWriter;
import gov.va.transpiler.node.OutputNode;
import gov.va.transpiler.node.ParentNode;

public class QueryNode extends ParentNode {

    private AliasedQuerySourceNode source;
    private WhereNode where;

    @Override
    public boolean addChild(OutputNode child) {
        // TODO: support multiple sources/where clauses/other Query features
        if (child instanceof AliasedQuerySourceNode) {
            if (source != null) {
                return false;
            }
            source = (AliasedQuerySourceNode) child;
            return true;
        } else if (child instanceof WhereNode) {
            if (where != null) {
                return false;
            }
            where = (WhereNode) child;
            return true;
        }
        else {
            return super.addChild(child);
        }
    }

    @Override
    public String asOneLine() {
        return null;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        boolean printed = source.print(outputWriter);
        printed |= where.print(outputWriter);
        outputWriter.printFullLine("returnVal = " + where.getScope());
        return printed;
    }
}
