package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait CodeLike extends ElementLike {
  def system: Uri
  def code: Code
  def version: Option[String]
  def display: Option[String]
  def userSelected: Option[String]
}

final case class Coding (
  system: Uri,
  code: Code,
  version: Option[String] = None,
  display: Option[String] = None,
  userSelected: Option[String] = None,
  id: Option[String] = None
) extends CodeLike