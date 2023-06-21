package gov.va.sparkcql.core.adapter.data

import scala.reflect.runtime.universe._
import scala.collection.JavaConverters._
import javax.xml.namespace.QName
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.core.adapter.Adapter

trait DataAdapter extends Adapter {
}