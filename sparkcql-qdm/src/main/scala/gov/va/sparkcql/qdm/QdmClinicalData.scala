package gov.va.sparkcql.core.model.qdm

import gov.va.sparkcql.core.model.ClinicalDataLike

case class QdmClinicalData(dataType: String, version: Option[String]) extends ClinicalDataLike {
  override val system = s"urn:healthit-gov:qdm"
}