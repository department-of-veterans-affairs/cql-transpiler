package gov.va.sparkcql.translator.result;

import java.util.List;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class DataResult extends Result {
    
    private String alias;
    private Column[] appliedColumns;
    private Dataset<Row> ds;

    public DataResult() {
    }

    public DataResult(DataResult from) {
        super(from);
        this.alias = from.alias;
        this.appliedColumns = from.appliedColumns;
        this.ds = from.ds;
    }

    public DataResult with(Column[] appliedColumns) {
        this.appliedColumns = appliedColumns;
        return this;
    }

    public DataResult with(Dataset<Row> ds) {
        this.ds = ds;
        return this;
    }

    public String alias() {
        return this.alias;
    }

    public Column[] appliedColumns() {
        return this.appliedColumns;
    }

    public Dataset<Row> ds() {
        return ds;
    }
}