package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.helper.FileHelper
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import gov.va.sparkcql.core.translation.cql2elm.CqlCompilerGateway
import gov.va.sparkcql.core.model.providable._
import gov.va.sparkcql.core.model.{DataType, ValueSet, CqlContent}
import org.json4s._
import org.json4s.jackson.Serialization.{read, write}
import gov.va.sparkcql.core.model.elm.VersionedIdentifier
import gov.va.sparkcql.core.adapter.model.ModelAdapter

protected case class FileContent(path: String, value: String)

class FileSourceAdapter(spark: SparkSession, path: String, modelAdapter: ModelAdapter) extends SourceAdapter(spark, modelAdapter) {

  private type JsonString = String

  val currentDir = FileHelper.currentDir()
  println(currentDir)
  
  lazy val fileContents = FileHelper.search(path, "*").map(c => {
    val ext = c.split("\\.").last
    FileContent(c.toLowerCase(), scala.io.Source.fromFile(c).mkString)
  }).toSeq

  def read(dataType: DataType): Option[Dataset[Row]] = {
    import spark.implicits._ 
    val jsonData = fileContents.flatMap(c => convert(dataType, c))
    if (jsonData.length > 0) {
      val schema = modelAdapter.schema(dataType)
      if (schema.isDefined) {
        Some(spark.read.schema(schema.get).json(jsonData.toDS()))
      } else {
        println(s"WARNING: No schema found for ${dataType.toString()}. Attempting to infer schema.")
        Some(spark.read.json(jsonData.toDS()))
      }
    } else {
      None
    }
  }

  def convert(dataType: DataType, content: FileContent): Option[JsonString] = {
    dataType match {
      case x if x == DataType[CqlContent]() && content.path.endsWith(".cql") =>
        val entity = modelAdapter.deserialize[CqlContent](content.value)
        implicit val formats: Formats = DefaultFormats
        Some(write(entity))
      case x if x == DataType[ValueSet]() && content.path.endsWith(s".${x.name.toLowerCase()}.json") =>
        Some(content.value)
      case _ => None
    }
  }
}