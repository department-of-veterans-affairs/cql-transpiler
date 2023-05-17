package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait Narratable extends Elementable {
  def status: Code
  def div: Xhtml
}

final case class Narrative (
  status: Code,
  div: Xhtml,
  id: Option[String] = None
) extends Narratable