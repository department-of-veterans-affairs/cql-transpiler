package gov.va.sparkcql.adapter.data;

import javax.xml.namespace.QName;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.adapter.Adapter;

public interface DataAdapter extends Adapter {

    public abstract Boolean isDataTypeDefined(QName dataType);

    public abstract Dataset<Row> acquireData(QName dataType);
}