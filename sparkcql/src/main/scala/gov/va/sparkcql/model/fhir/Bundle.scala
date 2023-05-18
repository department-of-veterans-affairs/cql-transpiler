package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait BundleEntryLike {
  val fullUrl: Uri
  val resource: Resource
}

final case class BundleEntry (
  fullUrl: Uri,
  resource: Resource
) extends BundleEntryLike

trait BundleLike extends ResourceLike {
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
) extends BundleLike