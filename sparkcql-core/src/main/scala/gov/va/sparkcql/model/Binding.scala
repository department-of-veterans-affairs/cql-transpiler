package gov.va.sparkcql.model

import scala.reflect.runtime.universe._
import gov.va.sparkcql.dataprovider.DataProvider
import org.apache.spark.sql.{Dataset, Row}
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.dataprovider.TableDataProvider

case class Binding[+BindableData : TypeTag](partialProvider: SparkSession => DataProvider) {
  val typeTag = typeOf[BindableData]
}