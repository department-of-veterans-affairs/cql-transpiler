package gov.va.sparkcql.executor;

import java.io.Serializable;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.entity.EvaluationResult;
import gov.va.sparkcql.entity.LibraryCollection;
import gov.va.sparkcql.entity.Plan;
import gov.va.sparkcql.model.Model;

public interface Executor extends Serializable {
    
    public Dataset<EvaluationResult> execute(LibraryCollection libraryCollection, Plan plan, List<Model> models, Engine engine, Dataset<Row> clinicalDs, Dataset<Row> terminologyDs);
}