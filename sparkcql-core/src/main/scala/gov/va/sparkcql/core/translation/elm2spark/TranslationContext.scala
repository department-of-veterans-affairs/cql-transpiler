package gov.va.sparkcql.core.translation.elm2spark

import org.apache.spark.sql.{Dataset, Row}
import org.hl7.elm.r1.{Library, ParameterDef}

trait Context
case object PatientContext extends Context
case object PractitionerContext extends Context

private case class TranslationContext(
  suppliedParameters: Option[Map[String, Object]],
  library: Option[Library] = None,
  dataset: Option[Dataset[Row]] = None,
  context: Context = PatientContext
)