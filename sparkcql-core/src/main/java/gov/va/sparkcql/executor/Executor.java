package gov.va.sparkcql.executor;

import java.io.Serializable;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.entity.ExecutionResult;
import gov.va.sparkcql.entity.LibraryCollection;
import gov.va.sparkcql.entity.Plan;

public interface Executor extends Serializable {
    
    public Dataset<ExecutionResult> execute(LibraryCollection libraryCollection, Plan plan, Dataset<Row> clinicalDs, Dataset<Row> terminologyDs);
}