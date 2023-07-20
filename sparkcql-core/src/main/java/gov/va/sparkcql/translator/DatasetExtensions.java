package gov.va.sparkcql.translator;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan;
import org.apache.spark.sql.catalyst.plans.logical.SubqueryAlias;

final public class DatasetExtensions {

    private DatasetExtensions() {
    }

    public static String getAlias(Dataset<?> dataset) {
        final LogicalPlan analyzed = dataset.queryExecution().analyzed();
        if (analyzed instanceof SubqueryAlias) {
            SubqueryAlias subqueryAlias = (SubqueryAlias) analyzed;
            return subqueryAlias.alias();
        }
        return null;
    }
}
