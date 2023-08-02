package gov.va.sparkcql.retriever;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class DataSourceAggregator implements DataSource {

    List<DataSource> dataSources;

    public DataSourceAggregator(List<DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    @Override
    public Boolean isDefined(QName dataType) {
        return dataSources.stream().filter(a -> a.isDefined(dataType)).count() > 0;
    }

    @Override
    public Dataset<Row> query(QName dataType) {
        List<DataSource> eligibleSources = dataSources.stream().filter(a -> a.isDefined(dataType)).toList();
        if (eligibleSources.size() > 0) {
            var queriedDS = eligibleSources.stream().map(adapter -> {
                var ds = adapter.query(dataType);
                if (ds == null) {
                    throw new RuntimeException(adapter.getClass().getSimpleName() + " stated support for data type '" + dataType.toString() + "' but none was found.");
                } else if (ds.schema().fields().length == 0) {
                    throw new RuntimeException(adapter.getClass().getSimpleName() + " returned a columnless dataframe when None should have been returned. Ignoring output.");
                } else {
                    return ds;
                }
            });
            var unionedDS = queriedDS.reduce((a, b) -> a.union(b));
            return unionedDS.get();
        } else {
            throw new RuntimeException("Attempted to query missing data for type '" + dataType.toString() + "' without verifying data was present.");
        }
    }
}