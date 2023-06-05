package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import gov.va.sparkcql.common.Files
import gov.va.sparkcql.converter.Converter
import gov.va.sparkcql.translation.CqlCompilerGateway
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import gov.va.sparkcql.model._

final case class FileContent(value: String)

class FileDataProvider(path: String, converter: Converter) extends DataProvider() {

  lazy val data = Files.search(path, ".cql").map(c => FileContent(scala.io.Source.fromFile(c).mkString)).toSeq
  
  def fetch[T <: Product](spark: SparkSession, filter: Option[List[FilterElement]])(implicit tag: TypeTag[T]): Dataset[T] = {
    val convertedData = data.map(d => converter.convert[FileContent, T](d))
    val encoder = Encoders.product[T]
    spark.createDataset(convertedData)(encoder)
  }

  def terminate(): Unit = {
  }
}