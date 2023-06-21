package gov.va.sparkcql.core.adapter.transformation

import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import javax.xml.namespace.QName
import gov.va.sparkcql.core.Log
import gov.va.sparkcql.core.adapter.{Composite}

sealed class CompositeTransformationAdapter(adapters: List[TransformationAdapter]) extends Composite with TransformationAdapter {
  
}