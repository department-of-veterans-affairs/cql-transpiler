package gov.va.sparkcql.evaluator

import gov.va.sparkcql.model.Model
import gov.va.sparkcql.source.Source
import gov.va.sparkcql.evaluator.node._
import org.apache.spark.sql.SparkSession
import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}

class Evaluator(models: List[Model], sources: List[Source], spark: SparkSession) {

  def evaluate(parameters: Map[String, Object], libraries: List[elm.Library]): EvaluationResult = {
    val context = Context(parameters, spark)
    val packageNode = new PackageNode(libraries)
    packageNode.evaluate(context).asInstanceOf[EvaluationResult]
  }
}