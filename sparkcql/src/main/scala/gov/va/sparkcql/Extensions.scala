// package gov.va.sparkcql

// import gov.va.sparkcql.model.fhir.r4.Coding
// import scala.reflect.runtime.universe._
// import org.apache.spark.sql.{SparkSession, Dataset, Row}
// import gov.va.sparkcql.model.fhir.r4.Encounter
// import gov.va.sparkcql.binding.{Binding, PredicateLike}
// import scala.collection.mutable.HashMap

// package object extensions {
  
//   val defaultBinding = Coding(system="default", code="default")
//   val bindings = HashMap[Coding, Binding]()

//   implicit class BuilderExtensions(builder: SparkSession.Builder) {
//     def enableCql(): SparkSession.Builder = {
//       // TODO: Perform initialization and verify initialized. Or drop the method altogether if unnecessary.
//       println("CQL Enabled")
//       builder
//     }
    
//     def withBinding[T <: BoundType : TypeTag](binding: Class[Binding]): SparkSession.Builder = {
//       println("Binded")
//       builder
//     }

//     def withBinding(types: List[BoundType], binding: Class[Binding]): SparkSession.Builder = {
//       println("Binded")
//       builder
//     }
//   }
// }


// //   def translate() { // f: A => U

// //   }
// //   // def translate(binder: (Coding, Option[List[PredicateLike]]) => Dataset[Row]): Unit = {
// //   // }

// //   def bind(binding: Binding, resourceType: Coding = defaultBinding): SparkCqlSession = {
// //     bindings(resourceType) = binding
// //     this
// //   }

// //   // .getOrElse(throw new Exception(s"ValueSet ${value.name.getOrElse(value.id)} is empty."))
// // }

// // object SparkCqlSession {
// //   def apply(): SparkCqlSession = {
// //     new SparkCqlSession()
// //   }
// // }