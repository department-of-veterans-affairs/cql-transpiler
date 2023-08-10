package gov.va.sparkcql.executor;

import org.apache.spark.api.java.function.MapPartitionsFunction;
import org.apache.spark.sql.Row;

public interface ExecutorMapPartition extends MapPartitionsFunction<Row, Row> {
}