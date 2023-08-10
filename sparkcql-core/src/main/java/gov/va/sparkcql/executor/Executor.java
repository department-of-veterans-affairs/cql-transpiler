package gov.va.sparkcql.executor;

import java.io.Serializable;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.hl7.elm.r1.Library;

import gov.va.sparkcql.model.EvaluationResult;
import gov.va.sparkcql.model.Plan;

public interface Executor extends Serializable {
    
    public Dataset<EvaluationResult> execute(List<Library> libraries, Plan plan, Dataset<Row> clinicalData, Dataset<Row> terminologyData);
}