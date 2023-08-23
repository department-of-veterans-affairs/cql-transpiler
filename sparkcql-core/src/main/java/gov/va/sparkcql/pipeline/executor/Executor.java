package gov.va.sparkcql.pipeline.executor;

import java.io.Serializable;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.domain.ExecutionResult;
import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.Component;

public interface Executor extends Component, Serializable {
    
    public Dataset<ExecutionResult> execute(LibraryCollection libraryCollection, Plan plan, Dataset<Row> clinicalDs, Dataset<Row> terminologyDs);
}