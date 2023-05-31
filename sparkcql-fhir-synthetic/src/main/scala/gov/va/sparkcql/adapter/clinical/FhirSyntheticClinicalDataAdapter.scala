package gov.va.sparkcql.adapter.clinical

//import java.nio.file.{FileSystems, Files}
import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, DataFrame, Row}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.hl7.elm.r1.Code

class FhirSyntheticClinicalDataAdapter(size: FhirSyntheticClinicalDataAdapter.PopulationSize) extends TableClinicalDataAdapter {

  def allResourceText(spark: SparkSession) = {
    import spark.implicits._
    // Load all files for given path into a Seq of strings.
    val bundleText = loadBundles(spark)
    
    val bundleParsed = spark.read.json(bundleText)
    bundleParsed
      .select(explode(col("entry")).as("entry"))
      .select(
        to_json(struct(col("entry.resource.*"))).as("resourceText"),
        col("entry.resource.resourceType").as("resourceType"))
  }

  def ensureFetched(spark: SparkSession, dataBindableType: Code): Unit = {
    import spark.implicits._
    val info = tableInfo(dataBindableType)
    val table = tableRef(dataBindableType)
    if (!spark.catalog.tableExists(table)) {
      val resourceText = allResourceText(spark)
        .where(col("resourceType").equalTo(dataBindableType.getCode()))    // TODO: ADD FILTER ON MODEL AND VERSION
        .select(col("resourceText"))
        .toDF()
            
      val ds = spark.read.json(resourceText.as[String])
        .select(struct("*").alias(DefaultResourceColumnName))

      ds.createTempView(table)
    }
  }

  override def retrieve(spark: SparkSession, dataBindableType: Code, filter: Option[List[Object]]): Option[DataFrame] = {
    import spark.implicits._
    ensureFetched(spark, dataBindableType)
    super.retrieve(spark, dataBindableType, filter)
  }

  def loadBundles(spark: SparkSession): Dataset[String] = {
    import spark.implicits._
    size match {
      case FhirSyntheticClinicalDataAdapter.PopulationSize10 =>
        val loader = Thread.currentThread().getContextClassLoader()
        import scala.io.Source
        Seq(
          Source.fromResource("embedded/Aaron697_Brekke496_2fa15bc7-8866-461a-9000-f739e425860a.json").mkString,
          Source.fromResource("embedded/Ali918_Stokes453_f5aa3408-57b3-4c05-a1d3-e4511b4be50e.json").mkString,
          Source.fromResource("embedded/Andreas188_Dare640_f7f63ca8-d282-4520-9a68-3177e2a5db6f.json").mkString,
          Source.fromResource("embedded/Despina962_Collier206_c3127664-ea4c-482c-953a-92f24da84bba.json").mkString,
          Source.fromResource("embedded/Erline657_Casper496_f8329bff-a048-4054-8a6b-208f54a2a330.json").mkString,
          Source.fromResource("embedded/Kent912_Ruecker817_f4b034c1-9e82-4e3a-8eda-96d1b938692f.json").mkString,
          Source.fromResource("embedded/Leopoldo762_Reynolds644_ef29d12a-a4a6-4b49-b100-66efa3a01987.json").mkString,
          Source.fromResource("embedded/Monserrate4_Carter549_aa465d13-1030-4fd4-a352-a1c615ea6df2.json").mkString,
          Source.fromResource("embedded/Sina65_Wolff180_582b89e2-30d8-44fb-bb96-03957b2ec7c2.json").mkString,
          Source.fromResource("embedded/Truman805_Durgan499_277bea41-b704-4be3-972a-4feee4e2712b.json").mkString
        ).toDS()
      case FhirSyntheticClinicalDataAdapter.PopulationSize1000 => ???
      case FhirSyntheticClinicalDataAdapter.PopulationSize1M => ???
      }
  }
}

object FhirSyntheticClinicalDataAdapter {
  sealed trait PopulationSize
  final case object PopulationSize10 extends PopulationSize
  final case object PopulationSize1000 extends PopulationSize
  final case object PopulationSize1M extends PopulationSize
}