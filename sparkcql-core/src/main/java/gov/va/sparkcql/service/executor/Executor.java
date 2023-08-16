package gov.va.sparkcql.service.executor;

import java.io.Serializable;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.domain.ExecutionResult;
import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.domain.Plan;

public interface Executor extends Serializable {
    
    public Dataset<ExecutionResult> execute(LibraryCollection libraryCollection, Plan plan, Dataset<Row> clinicalDs, Dataset<Row> terminologyDs);
}