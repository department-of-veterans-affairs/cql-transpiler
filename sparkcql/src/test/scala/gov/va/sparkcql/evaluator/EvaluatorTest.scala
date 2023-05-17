package gov.va.sparkcql.evaluator

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.types.DataTypes
import scala.collection.Seq
import org.apache.spark.sql.catalyst.ScalaReflection
import gov.va.sparkcql.model.fhir.Coding
import gov.va.sparkcql.Sparkable

class EvaluatorTest extends AnyFlatSpec with Sparkable {

  "An Evaluator" should "retrieve common FHIR resource types" in {

  }
  
  it should "work with case types" in {
    // val x = fixture.spark.sql("select 333 foo")
    // val y = fixture.spark.sparkContext.getConf.get("my.test.config")
    // println(y)

    // //x.show()
    // val a = new StructType()
    // val b = new StructField("Col1", DataTypes.StringType)
    // a.add(b)
    // val schema = ScalaReflection.schemaFor[Coding].dataType.asInstanceOf[StructType]

    // // val df = Seq(("A1", "B1", "C1", "D1", "E1"), ("A2", "B2", "C2", "D2", "E2")).toDF("Type","Month","Dept","Size","IsHoliday")
    // val expr = explode(col("foo"))
    // assert(2 == 2)
  }

  it should "work with valuesets" in {
    assert(1 == 1)
  }
}