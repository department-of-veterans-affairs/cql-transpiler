package gov.va.sparkcql.session

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import gov.va.sparkcql.compiler.CqlCompiler
import scala.collection.mutable.MutableList
import gov.va.sparkcql.dataprovider.{DataProvider, DataAdapter}
import gov.va.sparkcql.model.{Evaluation, VersionedIdentifier, LibraryData, DataTypeRef}
import gov.va.sparkcql.evaluator.ElmEvaluator

class SparkCqlSession private(builder: SparkCqlSession.Builder) {
  
  lazy val compiler = new CqlCompiler(builder.libraryDataProvider, Some(builder.spark))
  lazy val evaluator = new ElmEvaluator(builder.spark, builder.clinicalDataProvider, builder.terminologyDataProvider)
  lazy val clinicalDataAdapter = builder.clinicalDataProvider.map[DataAdapter](_.createAdapter(builder.spark)).headOption

  def retrieve[T <: Product : TypeTag](): Dataset[T] = {
    val adapter = clinicalDataAdapter.getOrElse(throw new Exception(s"No clinical data provider defined."))
    adapter.read[T]()
  }

  def retrieve(dataType: DataTypeRef): Dataset[Row] = {
    val adapter = clinicalDataAdapter.getOrElse(throw new Exception(s"No clinical data provider defined."))
    adapter.read(dataType)
  }

  def cql[T](cqlText: String): Evaluation = {
    cql(List(cqlText))
  }

  def cql[T](cqlText: List[String]): Evaluation = {
    cqlExec(Some(cqlText), None, None)
  }

  def cql[T](libraryIdentifiers: Seq[VersionedIdentifier]): Evaluation = {
    cqlExec(None, Some(libraryIdentifiers), None)
  }

  protected def cqlExec(
    cqlText: Option[List[String]],
    libraryIdentifiers: Option[Seq[VersionedIdentifier]],
    parameters: Option[Map[String, Object]]): Evaluation = {
    
    val compilation = if (cqlText.isDefined) { compiler.compile(cqlText.get) } else { compiler.compile(libraryIdentifiers.get) }
    val evaluation = evaluator.evalCompilation(compilation)
    evaluation
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

    def withLibraryData(provider: DataProvider): Builder = {
      libraryDataProvider = Some(provider)
      this
    }

    def withTerminologyData(provider: DataProvider): Builder = {
      terminologyDataProvider = Some(provider)
      this
    }

    def withClinicalData(provider: DataProvider): Builder = {
      clinicalDataProvider = Some(provider)
      this
    }

    def withClinicalData(system: String, provider: DataProvider): Builder = {
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