package gov.va.sparkcql.session

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import gov.va.sparkcql.compiler.CqlCompiler
import gov.va.sparkcql.evaluator.Evaluation
import scala.collection.mutable.MutableList
import gov.va.sparkcql.dataprovider.DataProvider
import gov.va.sparkcql.model.{VersionedIdentifier, LibraryData, DataTypeRef}

class SparkCqlSession private(builder: SparkCqlSession.Builder) {
  
  lazy val compiler = new CqlCompiler(builder.libraryDataProvider, Some(builder.spark))

  def retrieve[T <: Product : TypeTag](): Dataset[T] = {
    val clinicalDataProvider = builder.clinicalDataProvider.getOrElse(throw new Exception(s"No clinical data provider defined."))
    clinicalDataProvider.fetch[T](builder.spark)
  }

  def retrieve(dataType: DataTypeRef): Dataset[Row] = {
    val clinicalDataProvider = builder.clinicalDataProvider.getOrElse(throw new Exception(s"No clinical data provider defined."))
    clinicalDataProvider.fetch(dataType, builder.spark)
  }

  def cql[T](libraryContent: String): Evaluation = {
    cql(List(libraryContent))
  }

  def cql[T](cqlContent: List[String]): Evaluation = {
    val compilation = compiler.compile(cqlContent)
    Evaluation(null)
  }

  def cql[T](libraryIdentifiers: Seq[VersionedIdentifier]): Evaluation = {
    val compilation = compiler.compile(libraryIdentifiers)
    ???
  }

  protected def convertCompilationToEvaluation(): Evaluation = {
    ???
  }
}

object SparkCqlSession {

  class Builder private[SparkCqlSession](val spark: SparkSession) {

    var libraryDataProvider: Option[DataProvider] = None
    var terminologyDataProvider: Option[DataProvider] = None
    var clinicalDataProvider: Option[DataProvider] = None

    def withLibraryDataProvider(provider: DataProvider): Builder = {
      libraryDataProvider = Some(provider)
      this
    }

    def withTerminologyDataProvider(provider: DataProvider): Builder = {
      terminologyDataProvider = Some(provider)
      this
    }

    def withClinicalDataProvider(provider: DataProvider): Builder = {
      clinicalDataProvider = Some(provider)
      this
    }

    def withClinicalDataProvider(system: String, provider: DataProvider): Builder = {
      ???
    }

    def create(): SparkCqlSession = {
      new SparkCqlSession(this)
    }
  }

  def build(spark: SparkSession): Builder = {
    new Builder(spark)
  }
}