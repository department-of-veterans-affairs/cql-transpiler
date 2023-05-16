package gov.va.sparkcql.binding.factory

import gov.va.sparkcql.model.fhir.Coding
import scala.collection.mutable.HashSet
import gov.va.sparkcql.binding.strategy.BindingStrategy

class MatchedBindingFactory extends BindingFactory {

  type StrategyCreationMatch = (Coding) => Option[BindingStrategy]
  val registry = HashSet[StrategyCreationMatch]()

  def add(f: StrategyCreationMatch): Unit = {
    registry.add(f)
  }

  override def create(resourceType: Coding): BindingStrategy = {
    registry.foreach(f => {
      var strategy = f(resourceType)
      if (strategy.isDefined) {
        strategy
      }
    })
    throw new Exception(s"Unable to resolve binding strategy for identifier ${resourceType.toString()}")
  }
}
