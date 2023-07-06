package gov.va.sparkcql.translator

import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.adapter.model.{ModelAdapter, ModelAdapterAggregator}
import gov.va.sparkcql.adapter.data.{DataAdapter, DataAdapterAggregator}

final case class Environment(
  modelAdapters: List[ModelAdapter],
  dataAdapters: List[DataAdapter],
  parameters: Map[String, Object],
  spark: SparkSession
) {

  val modelAggregate = new ModelAdapterAggregator(modelAdapters)
  val dataAggregate = new DataAdapterAggregator(dataAdapters)
}