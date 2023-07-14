package gov.va.sparkcql.adapter.data;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class DataAdapterAggregator implements DataAdapter {

    List<DataAdapter> dataAdapters;

    public DataAdapterAggregator(List<DataAdapter> dataAdapters) {
        this.dataAdapters = dataAdapters;
    }

    @Override
    public Boolean isDataTypeDefined(QName dataType) {
        return dataAdapters.stream().filter(a -> a.isDataTypeDefined(dataType)).count() > 0;
    }

    @Override
    public Dataset<Row> acquireData(QName dataType) {
        List<DataAdapter> eligibleAdapters = dataAdapters.stream().filter(a -> a.isDataTypeDefined(dataType)).toList();
        if (eligibleAdapters.size() > 0) {
            var acquiredDS = eligibleAdapters.stream().map(adapter -> {
                var ds = adapter.acquireData(dataType);
                if (ds == null) {
                    throw new RuntimeException(adapter.getClass().getSimpleName() + " stated support for data type '" + dataType.toString() + "' but none was found.");
                } else if (ds.schema().fields().length == 0) {
                    throw new RuntimeException(adapter.getClass().getSimpleName() + " returned a columnless dataframe when None should have been returned. Ignoring output.");
                } else {
                    return ds;
                }
            });
            var unionedDS = acquiredDS.reduce((a, b) -> a.union(b));
            return unionedDS.get();
        } else {
            throw new RuntimeException("Attempted to acquire missing data for type '" + dataType.toString() + "' without verifying data was present.");
        }
    }
    
}
