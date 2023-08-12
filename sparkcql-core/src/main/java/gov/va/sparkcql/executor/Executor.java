package gov.va.sparkcql.executor;

import java.io.Serializable;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.model.EvaluationResult;
import gov.va.sparkcql.model.LibraryCollection;
import gov.va.sparkcql.model.Plan;

public interface Executor extends Serializable {
    
    public Dataset<EvaluationResult> execute(LibraryCollection libraryCollection, Plan plan, Dataset<Row> clinicalDs, Dataset<Row> terminologyDs);
}