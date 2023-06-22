package gov.va.sparkcql.model.qdm

import gov.va.sparkcql.model.ClinicalDataLike

case class QdmClinicalData(dataType: String, version: Option[String]) extends ClinicalDataLike {
  override val system = s"urn:healthit-gov:qdm"
}