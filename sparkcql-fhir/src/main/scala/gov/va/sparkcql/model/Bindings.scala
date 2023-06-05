package gov.va.sparkcql.model

import org.apache.spark.sql.{Dataset, Row}

case class Encounter(value: Dataset[Row]) extends ClinicalData {
  override val dataType = QName("", "")
}
//case class Condition() extends UntypedBindableData