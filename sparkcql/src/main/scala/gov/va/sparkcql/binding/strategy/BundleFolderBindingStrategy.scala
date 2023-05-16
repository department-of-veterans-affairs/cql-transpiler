package gov.va.sparkcql.binding.strategy

import org.apache.spark.sql.{DataFrame, SparkSession}
import gov.va.sparkcql.model.fhir._
import gov.va.sparkcql.model.fhir.Primitive._
import gov.va.sparkcql.model.elm.VersionedIdentifier

class BundleFolderBindingStrategy extends BindingStrategy {
  def resolve(spark: SparkSession, resourceType: VersionedIdentifier, code: Option[Coding], startDate: Option[DateTime], endDate: Option[DateTime]): DataFrame = {
    throw new Exception("Not Imp")
  }
}
// BundleFolderBindable