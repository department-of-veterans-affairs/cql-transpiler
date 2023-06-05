package gov.va.sparkcql.session

import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.model.Binding
import gov.va.sparkcql.model.VersionedIdentifier
import gov.va.sparkcql.compiler.CqlCompiler
import gov.va.sparkcql.evaluator.ElmEvaluation
import gov.va.sparkcql.model.BindableData

class SparkCqlSession private(builder: SparkCqlSession.Builder) {
  val spark = builder.spark
  val compiler = new CqlCompiler()

  def cql[T](libraryIdentifiers: Seq[VersionedIdentifier]): ElmEvaluation[T] = {
    ???
  }

  def cql[T](cqlContent: List[String]): ElmEvaluation[T] = {
    ???
  }

  def cql[T](libraryContent: String): ElmEvaluation[T] = {
    ???
    //compiler.compile(List(libraryContent))
  }
}

object SparkCqlSession {

  class Builder private[SparkCqlSession](val spark: SparkSession) {

    def create(): SparkCqlSession = {
      new SparkCqlSession(this)
    }

    def withBinding(binding: Binding[BindableData]): Builder = {
      // TODO
      this
    }

    // def withBinding(binding: List[Binding[BindableData]]): Builder = {
    //   // TODO
    //   this
    // }
  }

  def build(spark: SparkSession): Builder = {
    new Builder(spark)
  }
}