package gov.va.sparkcql.core.adapter.data

import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import javax.xml.namespace.QName
import gov.va.sparkcql.core.Log

sealed class CompositeDataAdapter(adapters: List[DataAdapter]) extends DataAdapter {
}