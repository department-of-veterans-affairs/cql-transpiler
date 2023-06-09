package gov.va.sparkcql.translation.elm2spark

import org.apache.spark.sql.{Dataset, Row}
import org.hl7.elm.r1.{ExpressionDef, VersionedIdentifier}

case class ElmDatasetLink(expressionDef: ExpressionDef, result: Dataset[Row], libraryRef: VersionedIdentifier)