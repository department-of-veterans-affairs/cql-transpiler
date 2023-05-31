package gov.va.sparkcql.translation.elm2spark

import org.apache.hadoop.shaded.org.eclipse.jetty.websocket.common.frames.DataFrame
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.dataprovider.clinical.ClinicalDataProvider
import gov.va.sparkcql.dataprovider.library.LibraryDataProvider
import gov.va.sparkcql.dataprovider.terminology.TerminologyDataProvider

// import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader

class ElmToSparkTranslator(spark: SparkSession, clinicalDataProvider: ClinicalDataProvider, terminologyDataProvider: TerminologyDataProvider, libraryDataProvider: LibraryDataProvider) {
  def translate[T](libraryId: Seq[String], parameters: Object): Seq[DataFrame] = {

    /*
    figure out how you want to break down elm processing into DS.
    Revisit DataProviders and their purpose (Term & Library)
    */
    ???
  }
  
  def translate(cqlText: String): Seq[DataFrame] = {
    ???
  }
}