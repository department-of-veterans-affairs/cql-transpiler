package gov.va.sparkcql.session

import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.binding.Binding
import gov.va.sparkcql.model.{LibraryId}

class SparkCqlSession private(builder: SparkCqlSession.Builder) {
  val spark = builder.spark

  def cql(libraryIdentifiers: Seq[LibraryId]): DataPackage = {
    ???
  }

  def cql(cqlContent: List[String]): DataPackage = {
    ???
  }

  def cql(cqlContent: String): DataPackage = {
    ???
  }  
}

object SparkCqlSession {

  class Builder(val spark: SparkSession) {
    def create(): SparkCqlSession = {
      new SparkCqlSession(this)
    }

    def withBinding(binding: Binding): Builder = {
      // TODO
      this
    }

    def withBinding(binding: List[Binding]): Builder = {
      // TODO
      this
    }
  }

  def build(spark: SparkSession): Builder = {
    new Builder(spark)
  }
}