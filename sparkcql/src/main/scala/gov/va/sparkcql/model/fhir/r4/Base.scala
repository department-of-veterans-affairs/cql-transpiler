package gov.va.sparkcql.model.fhir.r4

import gov.va.sparkcql.model.BoundType

trait BaseLike extends BoundType

final case class Base() extends BaseLike