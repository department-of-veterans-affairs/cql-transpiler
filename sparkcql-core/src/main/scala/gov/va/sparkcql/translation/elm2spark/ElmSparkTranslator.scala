package gov.va.sparkcql.translation.elm2spark

import org.apache.hadoop.shaded.org.eclipse.jetty.websocket.common.frames.DataFrame
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.adapter.clinical.ClinicalDataAdapter
import gov.va.sparkcql.adapter.library.LibraryDataAdapter
import gov.va.sparkcql.adapter.terminology.TerminologyDataAdapter

// import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader

class ElmSparkTranslator(spark: SparkSession, clinicalDataAdapter: ClinicalDataAdapter, terminologyDataAdapter: TerminologyDataAdapter, libraryDataAdapter: LibraryDataAdapter) {
  def translate[T](libraryId: Seq[String], parameters: Object): Seq[DataFrame] = {

    /*
    figure out how you want to break down elm processing into DS.
    Revisit DataAdapters and their purpose (Term & Library)
    */
    ???
  }
  
  def translate(cqlText: String): Seq[DataFrame] = {
    ???
  }
}