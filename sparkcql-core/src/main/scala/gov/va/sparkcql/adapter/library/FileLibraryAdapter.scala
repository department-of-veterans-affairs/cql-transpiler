package gov.va.sparkcql.adapter.library

import scala.reflect.runtime.universe._
import gov.va.sparkcql.io.Files
import org.json4s._
import org.json4s.jackson.Serialization.{read, write}
import javax.xml.namespace.QName
import org.apache.spark.sql.{SparkSession, DataFrame}
import gov.va.sparkcql.compiler.CompilerGateway
import gov.va.sparkcql.types._
import gov.va.sparkcql.logging.Log
import org.hl7.elm.r1.VersionedIdentifier

class FileLibraryAdapter(path: String) extends LibraryAdapter {

  assert(path != null)
  assert(path != "")

  val currentDir = Files.currentDir()
  
  lazy val contents = {
    Files.search(path, ".cql").flatMap(file => {
      val content = scala.io.Source.fromFile(file).mkString
      val identifier = CompilerGateway.parseVersionedIdentifier(content)
      Map(identifier -> content)
    }).toMap
  }

  def getLibraryContent(identifier: VersionedIdentifier): Option[String] = contents.get(identifier)
}