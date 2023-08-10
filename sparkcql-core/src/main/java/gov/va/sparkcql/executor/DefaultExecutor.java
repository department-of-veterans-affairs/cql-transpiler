package gov.va.sparkcql.executor;

import java.util.List;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.hl7.elm.r1.Library;

public class DefaultExecutor implements Executor {

    @Override
    public Map<String, Dataset<Row>> execute(List<Library> libraries, Dataset<Row> clinicalData, Dataset<Row> terminologyData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}