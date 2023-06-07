package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import gov.va.sparkcql.common.Files
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import gov.va.sparkcql.model._
import gov.va.sparkcql.compiler.CqlCompilerGateway
import org.json4s._
import org.json4s.jackson.JsonMethods._

final case class FileContent(path: String, ext: String, value: String)

class FileDataAdapter(spark: SparkSession, path: String) extends DataAdapter(spark) {
  val currentDir = Files.currentDir()
  println(currentDir)

  lazy val data = Files.search(path, "*").map(c => {
    val ext = c.split("\\.").last
    FileContent(c, ext, scala.io.Source.fromFile(c).mkString)
  }).toSeq

  def read(dataType: DataTypeRef, filter: Option[List[FilterElement]]): Dataset[Row] = {
    ???
  }

  def read[T <: Product : TypeTag](filter: Option[List[FilterElement]]): Dataset[T] = {
    val convertedData = data.map(d => convert[T](d)).asInstanceOf[Seq[T]]
    val encoder = Encoders.product[T]
    spark.createDataset(convertedData.asInstanceOf[Seq[T]])(encoder)
  }

  def convert[T <: Product : TypeTag](content: FileContent): T = {
    typeOf[T] match {
      case x if typeOf[T] <:< typeOf[LibraryData] =>
        val id = VersionedIdentifier(CqlCompilerGateway.parseVersionedIdentifier(content.value))
        new LibraryData(id, content.value).asInstanceOf[T]
      case x if typeOf[T] <:< typeOf[ValueSetData] =>
        implicit val formats: Formats = DefaultFormats
        parse(content.value).extract[ValueSetData].asInstanceOf[T]
      case _ => 
        throw new Exception("Unable to convert type " + typeOf[T].termSymbol.name)
    }
  }
}