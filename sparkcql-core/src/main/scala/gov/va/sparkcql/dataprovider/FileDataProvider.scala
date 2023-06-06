package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import gov.va.sparkcql.common.Files
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import gov.va.sparkcql.model._
import scala.collection.mutable.HashMap
import gov.va.sparkcql.compiler.CqlCompilerGateway

final case class FileContent(value: String)

class FileDataProvider(path: String) extends DataProvider() {

  lazy val data = Files.search(path, ".*").map(c => FileContent(scala.io.Source.fromFile(c).mkString)).toSeq

  def fetch(dataType: DataTypeRef, spark: SparkSession): Dataset[Row] = {
    ???
  }

  def fetch[T <: Product : TypeTag](filter: Option[List[FilterElement]], spark: SparkSession): Dataset[T] = {
    val convertedData = data.map(d => convert[T](d)).asInstanceOf[Seq[T]]
    val encoder = Encoders.product[T]
    spark.createDataset(convertedData.asInstanceOf[Seq[T]])(encoder)
  }

  def convert[T : TypeTag](content: FileContent): LibraryData = {
    typeOf[T] match {
      case x if typeOf[T] <:< typeOf[LibraryData] =>
        val id = VersionedIdentifier(CqlCompilerGateway.parseVersionedIdentifier(content.value))
        new LibraryData(id, content.value)
      case _ => throw new Exception("Unable to convert type " + typeOf[T].termSymbol.name)
    }
  }
}

object FileDataProvider {
  def apply(path: String) = new FileDataProvider(path)
}