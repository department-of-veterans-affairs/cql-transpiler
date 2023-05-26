package gov.va.sparkcql.session

import org.apache.spark.sql.{SparkSession, Row, Dataset, DataFrame}
import scala.reflect.runtime.universe._
import scala.collection.mutable.HashMap
import gov.va.sparkcql.binding.Binder
import gov.va.sparkcql.model.elm.{Code, ExpressionLike}
import java.util.ArrayList
import gov.va.sparkcql.binding.NullBinder

class CqlSession private[CqlSession](builder: CqlSession.Builder) {

  val spark = builder.spark
  val binding = builder.binding
  val cqlCompilerUrl = builder.cqlCompilerUrl

  def retrieve(bindableTypeCode: Code, filter: Option[List[ExpressionLike]] = None): Option[DataFrame] = {
    binding(bindableTypeCode).retrieve(this, bindableTypeCode, filter)
  }

  def evaluate[T](libraryId: String, parameters: Object): Seq[DataFrame] = {
    ???
  }
  
  def result[T](): Dataset[T] = {
    ???
  }

  def expression(cqlExpression: String): Seq[DataFrame] = {
    // TODO: Could/should this be a spark function similar to expr()?
    ???
  }
}

object CqlSession {
  type BindingFunction = (Code) => Binder
    
  def build(spark: SparkSession, binding: BindingFunction): Builder = {
    new Builder(spark, binding)
  }

  def build(spark: SparkSession, binder: Binder): Builder = {
    build(spark, code => binder)
  }
  
  val sparkToSessionMap = new HashMap[SparkSession, CqlSession]()

  protected class Builder(val spark: SparkSession, val binding: BindingFunction) {

    //var binding: Option[BindingFunction] = None
    var cqlCompilerUrl: Option[String] = None
    
    if (sparkToSessionMap.contains(spark)) throw new Exception("The spark session is already in use. Create an unused spark session with spark.newSession().")
    
    def withCqlCompilerUrl(url: String): Builder = {
      this.cqlCompilerUrl = Some(url)
      this
    }

    def create(): CqlSession = {
      val session = new CqlSession(this)
      sparkToSessionMap.put(spark, session)
      session
    }
  }
}