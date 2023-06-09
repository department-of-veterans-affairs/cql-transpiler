package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import gov.va.sparkcql.common.helper.FileHelper
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import gov.va.sparkcql.translation.cql2elm.CqlCompilerGateway
import org.json4s._
import org.json4s.jackson.JsonMethods._

class FileDataProvider(path: String) extends DataProvider {
  def createAdapter(spark: SparkSession): DataAdapter = {
    new FileDataAdapter(spark, path)
  }
}

object FileDataProvider {
  def apply(path: String) = new FileDataProvider(path)
}