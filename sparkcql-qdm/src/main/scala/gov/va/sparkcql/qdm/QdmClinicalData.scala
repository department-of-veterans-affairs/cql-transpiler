package gov.va.sparkcql.adapter.model.qdm

import gov.va.sparkcql.adapter.model.ClinicalDataLike

case class QdmClinicalData(dataType: String, version: Option[String]) extends ClinicalDataLike {
  override val system = s"urn:healthit-gov:qdm"
}