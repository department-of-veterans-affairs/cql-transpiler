package gov.va.sparkcql.adapter.clinical

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, DataFrame, Row}
import org.hl7.elm.r1.Code

/**
  * 
  */
trait ClinicalDataAdapter {

  def retrieve(spark: SparkSession, dataBindableType: Code, filter: Option[List[Object]] = None): Option[DataFrame]
  
}