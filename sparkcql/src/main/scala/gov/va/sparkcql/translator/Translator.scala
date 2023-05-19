package gov.va.sparkcql.translator

import gov.va.sparkcql.model.fhir.Coding
import gov.va.sparkcql.model.fhir.Primitive
import scala.reflect.runtime.universe._
import org.apache.spark.sql.{Dataset, Row}
import gov.va.sparkcql.model.fhir.Encounter
import gov.va.sparkcql.binding.{Bindable, PredicateLike}
import scala.collection.mutable.HashMap

class Translator private() {

  val defaultBinding = Coding(system="default", code="default")
  val bindings = HashMap[Coding, Bindable]()

  def translate() { // f: A => U

  }
  // def translate(binder: (Coding, Option[List[PredicateLike]]) => Dataset[Row]): Unit = {
  // }

  def bind(binding: Bindable, resourceType: Coding = defaultBinding): Translator = {
    bindings(resourceType) = binding
    this
  }

  // .getOrElse(throw new Exception(s"ValueSet ${value.name.getOrElse(value.id)} is empty."))
}

object Translator {
  def apply(): Translator = {
    new Translator()
  }
}