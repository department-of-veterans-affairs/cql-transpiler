package gov.va.sparkcql.evaluator

import gov.va.sparkcql.adapter.model.ModelAdapter
import gov.va.sparkcql.adapter.data.DataAdapter
import gov.va.sparkcql.evaluator.node._
import org.apache.spark.sql.SparkSession
import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}

class Evaluator(modelAdapters: List[ModelAdapter], dataAdapters: List[DataAdapter], spark: SparkSession) {

  def evaluate(parameters: Map[String, Object], libraries: List[elm.Library]): EvaluationResult = {
    val context = Context(parameters, spark)
    val packageNode = new PackageNode(libraries)
    packageNode.evaluate(context).asInstanceOf[EvaluationResult]
  }
}