package gov.va.sparkcql.translation

import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor
import org.hl7.elm.r1.Overlaps
import org.hl7.elm.r1.Library

class ElmLibraryVisitor extends ElmBaseLibraryVisitor[String, Object] {
  override def visitLibrary(elm: Library, context: Object): String = {
    ???
  }
}