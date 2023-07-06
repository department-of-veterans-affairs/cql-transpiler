package gov.va.sparkcql.translator

import gov.va.sparkcql.adapter.model.ModelAdapter
import gov.va.sparkcql.adapter.data.DataAdapter
import gov.va.sparkcql.translator.node._
import org.apache.spark.sql.SparkSession
import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}

class Translator(modelAdapters: List[ModelAdapter], dataAdapters: List[DataAdapter], spark: SparkSession) {

  def translate(parameters: Map[String, Object], libraries: List[elm.Library]): TranslationPack = {
    val env = Environment(modelAdapters, dataAdapters, parameters, spark)
    val packageNode = new PackageNode(libraries)
    packageNode.translate(env).asInstanceOf[TranslationPack]
  }
}