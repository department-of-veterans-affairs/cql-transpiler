package gov.va.sparkcql.adapter.data

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, DataFrame}
import javax.xml.namespace.QName

trait DataAdapter {

  val spark: SparkSession

  def isDataTypeDefined(dataType: QName): Boolean
  
  def acquire(dataType: QName): Option[DataFrame]
}