package gov.va.sparkcql.model.fhir.r4

trait PeriodLike extends ElementLike {
  val start: Option[DateTime]
  val end: Option[DateTime]
}

final case class Period (
  id: Option[String] = None,
  start: Option[DateTime] = None,
  end: Option[DateTime] = None
) extends PeriodLike