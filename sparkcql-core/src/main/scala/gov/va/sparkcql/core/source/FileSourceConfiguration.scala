package gov.va.sparkcql.core.source

import scala.reflect.runtime.universe._

case class FileSourceConfiguration(path: String) extends SourceConfiguration