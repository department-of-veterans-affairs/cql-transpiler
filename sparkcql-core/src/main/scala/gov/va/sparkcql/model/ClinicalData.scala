package gov.va.sparkcql.model

import org.apache.spark.sql.Row

trait ClinicalDataLike extends BindableData {
  val system: String
  val dataType: String
  val version: Option[String]
}

case class ClinicalData(system: String, dataType: String, version: Option[String]) extends ClinicalDataLike

case class TestClinicalData(val values: Array[Any]) extends ClinicalDataLike with Row {

  override val system: String = "testsystem"

  override val dataType: String = "testdatatype"

  override val version: Option[String] = Some("version")

  override def length: Int = values.length

  override def get(i: Int): Any = values(i)

  override def toSeq: Seq[Any] = values.clone()

  override def copy(): TestClinicalData = this
 
}

case class TestEncounter (
  id: String,
  status: String
  // val theClass: 
) extends BindableData