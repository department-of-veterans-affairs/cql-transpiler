// package gov.va.sparkcql.session

// import org.scalatest.flatspec.AnyFlatSpec
// import org.apache.spark.sql.{SparkSession, Dataset, Row}
// import org.apache.spark.sql.functions._
// import org.apache.spark.sql.types._
// import org.apache.spark.sql.types.DataTypes
// import scala.collection.Seq
// import org.apache.spark.sql.catalyst.ScalaReflection
// import gov.va.sparkcql.model.fhir.r4.Coding
// import gov.va.sparkcql.Sparkable
// import gov.va.sparkcql.binding.{Binding, PredicateLike}
// import gov.va.sparkcql.binding.MockDataBinding
// import scala.reflect.runtime.universe._
// import gov.va.sparkcql.extensions._
// import gov.va.sparkcql.model.fhir.r4._

// class ExtensionsTest extends AnyFlatSpec with Binding {

//   def bundleBinding(spark: SparkSession) = new MockDataBinding(spark, """"../data/fhir/bundle""")

//   def retrieve[T <: BoundType : TypeTag](filter: Option[List[PredicateLike]]): Option[Dataset[T]] = {
//     resourceType match {
//       case "" => ""
//       case _ => "nothing"
//     }
//     ???
//   }

//   "Extensions" should "retrieve common FHIR resource types" in {
    
//     val spark = SparkSession.builder()
//       .master("local[4]")
//       .enableCql()
//       .withBinding[Condition](null)
//       .withBinding(null)
//       .getOrCreate()

//     // 
//     // .withBinding[Encounter](bundleBinding(spark))
//     //.bind(this)
//   }
  
//   it should "work with case types" in {
//   }

//   it should "work with valuesets" in {
//     assert(1 == 1)
//   }

//   def test[T <: Base : TypeTag](): Unit = {
//     typeOf[T] match {
//       case t if t =:= typeOf[Condition] => "Condition"
//       case t if t =:= typeOf[Encounter] => "Encounter"
//       case _ => "nothing"
//     }
//   }
// }