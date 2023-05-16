package gov.va.sparkcql.model.fhir
import com.github.nscala_time.time.Imports.{DateTime => nscala_time}

object Primitive {
  type Uri = String
  type Code = String
  type Id = String
  type Instant = DateTime
  type DateTime = nscala_time
  type Xhtml = String
}