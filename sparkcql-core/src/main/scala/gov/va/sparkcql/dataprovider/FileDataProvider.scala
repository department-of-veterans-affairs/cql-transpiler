package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import gov.va.sparkcql.common.Files
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import gov.va.sparkcql.model._
import scala.collection.mutable.HashMap
import gov.va.sparkcql.compiler.CqlCompilerGateway

final case class FileContent(value: String)

class FileDataProvider(spark: SparkSession, path: String) extends DataProvider(spark) {

  lazy val data = Files.search(path, ".cql").map(c => FileContent(scala.io.Source.fromFile(c).mkString)).toSeq

  def fetch[T <: Product : TypeTag](filter: Option[List[FilterElement]]): Dataset[T] = {
    val convertedData = data.map(d => convert[T](d)).asInstanceOf[Seq[LibraryData]]
    val encoder = Encoders.product[T]
    spark.createDataset(convertedData.asInstanceOf[Seq[T]])(encoder)
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
  def apply(path: String) = (spark: SparkSession) => new FileDataProvider(spark, path)  
}