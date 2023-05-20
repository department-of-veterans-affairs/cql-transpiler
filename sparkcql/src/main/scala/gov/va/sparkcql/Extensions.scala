package gov.va.sparkcql

import gov.va.sparkcql.model.fhir.r4.Coding
import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import gov.va.sparkcql.model.fhir.r4.Encounter
import gov.va.sparkcql.binding.{Bindable, PredicateLike}
import scala.collection.mutable.HashMap

package object extensions {
  
  val defaultBinding = Coding(system="default", code="default")
  val bindings = HashMap[Coding, Bindable]()

  implicit class BuilderExtensions(builder: SparkSession.Builder) {
    def enableCql(): SparkSession.Builder = {
      println("CQL Enabled")
      builder
    }
  }
}


//   def translate() { // f: A => U

//   }
//   // def translate(binder: (Coding, Option[List[PredicateLike]]) => Dataset[Row]): Unit = {
//   // }

//   def bind(binding: Bindable, resourceType: Coding = defaultBinding): SparkCqlSession = {
//     bindings(resourceType) = binding
//     this
//   }

//   // .getOrElse(throw new Exception(s"ValueSet ${value.name.getOrElse(value.id)} is empty."))
// }

// object SparkCqlSession {
//   def apply(): SparkCqlSession = {
//     new SparkCqlSession()
//   }
// }