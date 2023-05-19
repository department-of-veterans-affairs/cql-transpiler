package gov.va.sparkcql.translator

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.types.DataTypes
import scala.collection.Seq
import org.apache.spark.sql.catalyst.ScalaReflection
import gov.va.sparkcql.model.fhir.Coding
import gov.va.sparkcql.Sparkable
import gov.va.sparkcql.binding.{Bindable, PredicateLike}
import gov.va.sparkcql.binding.BundleFolderBinding
import scala.reflect.runtime.universe._

class TranslatorTest extends AnyFlatSpec with Sparkable with Bindable {

  lazy val bundleBinding = new BundleFolderBinding(spark, """"../data/fhir/bundle""")

  def retrieve[T <: Product : TypeTag](resourceType: Coding, filter: Option[List[PredicateLike]]): Option[Dataset[T]] = {
    resourceType match {
      //case Coding(system, "Library", _, _, _, _) => None
      //case _ => bundleBinding.bind(resourceType, filter)
      case _ => None
    }
  }

  "An Translator" should "retrieve common FHIR resource types" in {
    val translator = Translator()
    .bind(this)
  }
  
  it should "work with case types" in {
  }

  it should "work with valuesets" in {
    assert(1 == 1)
  }
}