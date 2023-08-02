package gov.va.sparkcql.retriever;

import javax.xml.namespace.QName;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface DataSource {

    public abstract Boolean isDefined(QName dataType);

    public abstract Dataset<Row> query(QName dataType);    // TODO: FILTERS
}