package gov.va.sparkcql.session

//import org.apache.spark.sql
import org.scalatest.flatspec.AnyFlatSpec
import gov.va.sparkcql.binding._
import gov.va.sparkcql.Sparkable
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.model.fhir.r4.ValueSet
import gov.va.sparkcql.binding.MockDataBinding
import gov.va.sparkcql.model.fhir.r4.Condition
import com.google.common.io.Resources
import java.nio.charset.StandardCharsets
class SessionTest extends AnyFlatSpec with Sparkable {

  def sessionConfigurationJson: String = {
    val url = Resources.getResource("session-configuration.json")
    Resources.toString(url, StandardCharsets.UTF_8)
  }

  //def bundleBinding(spark: SparkSession) = new MockDataBinding(spark, """"../data/fhir/bundle""")

  "A Session" should "retrieve common FHIR resource types" in {
    Session(spark, sessionConfigurationJson)
      .retrieve[Condition]()
      .get.show()

    // .withBinding("gov.va.sparkcql.binding.MockDataBinding")
    // .withBinding[Condition]("gov.va.sparkcql.binding.MockDataBinding")
    // .withConfig("", "")
  }
}