package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.{ClinicalData, BindableData, QName}

import org.apache.spark.sql.{Dataset, Row}

trait Test extends Row

case class Encounter(value: Dataset[Row]) extends ClinicalData {
  override val dataType = QName("http://hl7.org/fhir", "Encounter")
}

trait FhirConditionDataLike extends Row with BindableData {
  val dataType = QName("http://hl7.org/fhir", "Condition")
}

case class Condition(val values: Array[Any]) extends Row with BindableData {
  protected def this() = this(null)
  def this(size: Int) = this(new Array[Any](size))
  override def length: Int = values.length
  override def get(i: Int): Any = values(i)
  override def toSeq: Seq[Any] = values.clone()
  override def copy(): Condition = this
}