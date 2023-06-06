package gov.va.sparkcql.session

import scala.reflect.runtime.universe._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.model.Binding
import gov.va.sparkcql.model.VersionedIdentifier
import gov.va.sparkcql.compiler.CqlCompiler
import gov.va.sparkcql.evaluator.Evaluation
import gov.va.sparkcql.model.BindableData
import scala.collection.mutable.MutableList
import gov.va.sparkcql.dataprovider.DataProvider
import gov.va.sparkcql.model.LibraryData
import org.apache.spark.sql.Dataset

class SparkCqlSession private(spark: SparkSession, boundProviders: Map[Type, DataProvider]) {
  
  lazy val compiler = new CqlCompiler(bound[LibraryData](), Some(spark))

  def retrieve[T <: Product : TypeTag](): Dataset[T] = {
    val clinicalDataProvider = bound[T]().getOrElse(throw new Exception(s"No binding for type ${typeOf[T].termSymbol}."))
    clinicalDataProvider.fetch[T]()
  }

  def expression[T](cqlExpression: String): Dataset[T] = {
    val compilation = compiler.compile(List(cqlExpression))
    ???
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

  protected def bound[T : TypeTag](): Option[DataProvider] = {
    Some(boundProviders(typeOf[T]))
    // val matched = boundProviders.filter(p => {
    //   typeOf[T] match {
    //     case p.typeTag => true
    //     case _ => false
    //   }
    // })
    // if (matched.size > 0) {
    //   Some(matched.head.provider)
    // } else {
    //   None
    // }
  }

  protected def convertCompilationToEvaluation(): Evaluation = {
    ???
  }
}

object SparkCqlSession {

  class Builder private[SparkCqlSession](val spark: SparkSession) {

    private val bindings = MutableList[Binding[BindableData]]()

    def withBinding(binding: Binding[BindableData]): Builder = {
      bindings += binding
      this
    }

    def withBinding(binding: List[Binding[BindableData]]): Builder = {
      bindings ++= binding
      this
    }

    def create(): SparkCqlSession = {
      val boundProviders = bindings.map(f => (f.typeTag, f.partialProvider(spark))).toMap
      new SparkCqlSession(spark, boundProviders)
    }
  }

  def build(spark: SparkSession): Builder = {
    new Builder(spark)
  }
}