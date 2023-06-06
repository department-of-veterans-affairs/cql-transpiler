package gov.va.sparkcql.evaluator

import org.apache.spark.sql.{Dataset, Row}
import gov.va.sparkcql.model.VersionedIdentifier

case class Evaluation(defineMap: Map[StatementDef, Seq[Dataset[Row]]])
case class StatementDef(library: VersionedIdentifier, defineName: String)

// class DataPackage {
//   /*
//   Should be structured
//   Evaluation Results? Data Evaluation, Evaluation Package, Data Package (*), Data Collection, Data Manifest
//     Errors
//     Library
//       Datasets Map
//   */
// }