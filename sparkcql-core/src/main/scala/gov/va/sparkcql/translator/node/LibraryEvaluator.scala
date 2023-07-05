package gov.va.sparkcql.translator.node

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import org.hl7.cql_annotations.r1.CqlToElmError
import gov.va.sparkcql.translator._

class LibraryNode(val element: elm.Library) extends Node {

  override protected def resolveChildren(): List[Object] = {
    (
      (if (element.getCodeSystems() == null) { List() } else { element.getCodeSystems().getDef().asScala }) ++
      (if (element.getCodes() == null) { List() } else { element.getCodes().getDef().asScala }) ++
      (if (element.getConcepts() == null) { List() } else { element.getConcepts().getDef().asScala }) ++
      (if (element.getContexts() == null) { List() } else { element.getContexts().getDef().asScala }) ++
      (if (element.getIncludes() == null) { List() } else { element.getIncludes().getDef().asScala }) ++
      (if (element.getParameters() == null) { List() } else { element.getParameters().getDef().asScala }) ++
      (if (element.getStatements() == null) { List() } else { element.getStatements().getDef().asScala }) ++
      (if (element.getUsings() == null) { List() } else { element.getUsings().getDef().asScala }) ++
      (if (element.getValueSets() == null) { List() } else { element.getValueSets().getDef().asScala })
    ).toList
  }

  override def translate(context: Context): Object = {
    val errors = element.getAnnotation().asScala.filter(p => p.isInstanceOf[CqlToElmError]).map(_.asInstanceOf[CqlToElmError])

    if (errors.length == 0) {
      val exprDefs = children[elm.ExpressionDef]()
      val exprEvals = exprDefs.map(_.translate(context).asInstanceOf[ExpressionDefTranslation])
      LibraryTranslation(element, exprEvals)
    } else {
      throw new Exception(errors.head.getMessage())
    }
  }
}