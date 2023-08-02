package gov.va.sparkcql.retriever;

import javax.xml.namespace.QName;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class TableDataSource implements DataSource {

    @Override
    public Boolean isDefined(QName dataType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isPresent'");
    }

    @Override
    public Dataset<Row> query(QName dataType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'query'");
    }
}