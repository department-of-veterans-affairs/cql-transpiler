package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait BundleEntryable {
  def fullUrl: Uri
  def resource: String
}

final case class BundleEntry (
  fullUrl: Uri,
  resource: Resource
)

trait Bundleable extends Resourceable {
  def identifier: Option[List[Identifier]]
  def `type`: Code
  def timestamp: Instant
  def entry: List[BundleEntry]
}

final case class Bundle (
  id: Id,
  resourceType: Option[String],
  meta: Option[Meta],
  implicitRules: Option[Uri],
  language: Option[Code],
  identifier: Option[List[Identifier]],
  `type`: Code,
  timestamp: Instant,
  entry: List[BundleEntry]
) extends Bundleable