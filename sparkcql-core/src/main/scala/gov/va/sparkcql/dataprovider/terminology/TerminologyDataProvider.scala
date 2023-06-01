package gov.va.sparkcql.dataprovider.terminology

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, DataFrame, Row}
import org.hl7.elm.r1.Code

abstract class TerminologyDataProvider(spark: SparkSession) {
  def retrieve(dataType: Code, filter: Option[List[Object]] = None): Option[DataFrame]
  def bind(dataType: Code, filter: Option[List[Object]] = None): Option[DataFrame]
  def fetchValueSet()
  def getCodeSystem()
}