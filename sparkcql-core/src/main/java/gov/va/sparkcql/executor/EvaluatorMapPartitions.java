package gov.va.sparkcql.executor;

import java.util.Iterator;

import org.apache.spark.api.java.function.MapPartitionsFunction;
import org.apache.spark.sql.Row;

public class EvaluatorMapPartitions implements MapPartitionsFunction<Row, Row> {

    @Override
    public Iterator<Row> call(Iterator<Row> input) throws Exception {
        return null;
    }
    
}