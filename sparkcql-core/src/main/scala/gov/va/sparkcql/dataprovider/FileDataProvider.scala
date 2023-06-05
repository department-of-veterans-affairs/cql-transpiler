package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import gov.va.sparkcql.common.Files
import gov.va.sparkcql.converter.Convertable
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import gov.va.sparkcql.model._
import scala.collection.mutable.HashMap
import gov.va.sparkcql.compiler.CqlCompilerGateway

final case class FileContent(value: String)

class FileDataProvider(path: String) extends DataProvider() {

  lazy val data = Files.search(path, ".cql").map(c => FileContent(scala.io.Source.fromFile(c).mkString)).toSeq

  def initialize(spark: SparkSession): Unit = {
  }

  def fetch[T <: Product](spark: SparkSession, filter: Option[List[FilterElement]])(implicit tag: TypeTag[T]): Dataset[T] = {
    val convertedData = data.map(d => convert[T](d)).asInstanceOf[Seq[LibraryData]]
    val encoder = Encoders.product[T]
    spark.createDataset(convertedData.asInstanceOf[Seq[T]])(encoder)
  }

  def terminate(): Unit = {
  }

  def convert[T : TypeTag](content: FileContent): LibraryData = {
    typeOf[T] match {
      //case x: LibraryData => 
      case x if typeOf[T] <:< typeOf[LibraryData] =>
        val id = VersionedIdentifier(CqlCompilerGateway.parseVersionedIdentifier(content.value))
        new LibraryData(id, content.value)
      case _ => throw new Exception("Unable to convert type " + typeOf[T].termSymbol.name)
    }
  }
}

object FileDataProvider {

  def apply(path: String): FileDataProvider = {
    new FileDataProvider(path)
  }
}