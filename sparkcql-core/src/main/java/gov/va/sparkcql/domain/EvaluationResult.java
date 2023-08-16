package gov.va.sparkcql.domain;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.types.DataTypedValue;

public class EvaluationResult extends GenericResult<DataTypedValue<Dataset<Row>>, EvaluationResult> {
}