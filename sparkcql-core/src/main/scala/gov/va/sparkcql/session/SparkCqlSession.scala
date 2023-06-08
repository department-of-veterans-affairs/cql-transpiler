package gov.va.sparkcql.session

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import gov.va.sparkcql.compiler.Compiler
import scala.collection.mutable.MutableList
import gov.va.sparkcql.dataprovider._
import gov.va.sparkcql.dataprovider.DataTypeRef
import gov.va.sparkcql.model.ext.elm.VersionedIdentifier
import gov.va.sparkcql.evaluator.{Evaluator, Evaluation}

class SparkCqlSession private(builder: SparkCqlSession.Builder) {
  
  lazy val compiler = new Compiler(builder.libraryDataProvider, Some(builder.spark))
  lazy val clinicalDataAdapter = builder.clinicalDataProvider.map[DataAdapter](_.createAdapter(builder.spark)).headOption
  lazy val terminologyDataAdapter = builder.clinicalDataProvider.map[DataAdapter](_.createAdapter(builder.spark)).headOption
  lazy val evaluator = new Evaluator(None, builder.spark, clinicalDataAdapter, terminologyDataAdapter)

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
    val evaluation = evaluator.eval(compilation)
    evaluation.asInstanceOf[Evaluation]
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