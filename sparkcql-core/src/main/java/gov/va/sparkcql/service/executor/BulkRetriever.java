package gov.va.sparkcql.service.executor;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.domain.Plan;

public interface BulkRetriever {

    public Dataset<Row> retrieve(Plan plan, Object terminologyProvider);
}