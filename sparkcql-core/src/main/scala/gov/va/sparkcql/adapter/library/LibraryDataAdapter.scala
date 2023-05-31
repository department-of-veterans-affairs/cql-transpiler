package gov.va.sparkcql.adapter.library

import scala.reflect.runtime.universe._
import org.cqframework.cql.cql2elm.LibrarySourceProvider
import org.hl7.elm.r1.Code

/**
  * 
  */
trait LibraryDataAdapter extends LibrarySourceProvider {

  // def retrieve(spark: SparkSession, dataBindableType: Code, filter: Option[List[Object]] = None): Option[DataFrame]
  
}