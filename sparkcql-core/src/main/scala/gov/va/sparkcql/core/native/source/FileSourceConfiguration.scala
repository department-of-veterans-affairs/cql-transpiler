package gov.va.sparkcql.core.native.source

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.source.SourceConfiguration

case class FileSourceConfiguration(path: String) extends SourceConfiguration