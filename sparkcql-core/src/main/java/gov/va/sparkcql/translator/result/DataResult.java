package gov.va.sparkcql.translator.result;

import java.util.List;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class DataResult extends Result {
    
    private String alias;
    private List<Column> appliedColumns;
    private Dataset<Row> ds;

    public DataResult(List<Result> children, Dataset<Row> ds, List<Column> appliedColumns, String alias) {
        super(children);
        this.alias = alias;
        this.appliedColumns = appliedColumns;
        this.ds = ds;
    }

    public String alias() {
        return this.alias;
    }

    public List<Column> appliedColumns() {
        return this.appliedColumns;
    }

    public Dataset<Row> ds() {
        return ds;
    }
}