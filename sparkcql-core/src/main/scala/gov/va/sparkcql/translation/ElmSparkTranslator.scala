package gov.va.sparkcql.translation

import org.apache.hadoop.shaded.org.eclipse.jetty.websocket.common.frames.DataFrame
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.dataprovider.{ClinicalDataProvider, TerminologyDataProvider, LibraryDataProvider}

// import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader

class ElmSparkTranslator(spark: SparkSession, clinicalDataProvider: ClinicalDataProvider, terminologyDataProvider: TerminologyDataProvider, libraryDataProvider: LibraryDataProvider) {
  def translate[T](libraryId: Seq[String], parameters: Object): Seq[DataFrame] = {
    ???
  }
  
  def translate(cqlText: String): Seq[DataFrame] = {
    ???
  }
}