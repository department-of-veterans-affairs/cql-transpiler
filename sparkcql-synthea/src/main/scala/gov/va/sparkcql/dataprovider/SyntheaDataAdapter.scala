package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._
import scala.collection.mutable.HashMap

class SyntheaDataAdapter(spark: SparkSession, size: PopulationSize) extends DataAdapter(spark) {

  import spark.implicits._

  val TablePrefix = "Synthea"
  val DefaultResourceColumnName = "resource_data"
  val BundlesResourceType = "Bundles"

  val dfCache = HashMap[String, Dataset[Row]]()

  lazy val bundles = {
    SyntheaDataLoader.loadBundles(size)
  }

  lazy val dfBundles = {
    spark.read.json(bundles.toDS)
      .select(explode(col("entry")).as("entry"))
      .select(
        to_json(struct(col("entry.resource.*"))).as(DefaultResourceColumnName),
        col("entry.resource.resourceType").as("resourceType"))
  }

  def createDataSet(resourceType: String): Dataset[Row] = {
    dfCache.getOrElseUpdate(resourceType, {
      val resourceText = dfBundles
        .where(col("resourceType").equalTo(resourceType))
        .select(col(DefaultResourceColumnName))
        .toDF()

      spark.read.json(resourceText.as[String])
    })
  }

  def read(dataType: DataTypeRef, filter: Option[List[FilterElement]]): Dataset[Row] = {
    val resourceType = dataType.id
    createDataSet(resourceType)
  }

  def read[T <: Product : TypeTag](filter: Option[List[FilterElement]]): Dataset[T] = {
    import spark.implicits._
    val tag = typeOf[T]
    
    val resourceType = tag.typeSymbol.name.toTypeName.toString()
    val df = createDataSet(resourceType)
    df.as[T]
  }
}

object SyntheaDataAdapter {
  val instances = HashMap[PopulationSize, SyntheaDataProvider]()

  def apply(size: PopulationSize) = {
    instances.getOrElseUpdate(size, new SyntheaDataProvider(size))
  }
}