package gov.va.sparkcql.translator.node

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.translator._

class PackageNode(val element: List[elm.Library]) extends Node {

  override protected def resolveChildren(): List[Object] = element
  
  override def translate(context: Context): Object = {
    val libraryEvals = children.map(l => {
      l.translate(context).asInstanceOf[LibraryTranslation]
    })
    TranslationPack(context.parameters, libraryEvals)
  }
}