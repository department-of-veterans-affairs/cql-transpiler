package gov.va.sparkcql.binding.factory

import gov.va.sparkcql.model.fhir.Coding
import scala.collection.mutable.HashSet
import gov.va.sparkcql.binding.Binding

class MatchFactory extends BindingFactory {

  type StrategyCreationMatch = (Coding) => Option[Binding]
  val registry = HashSet[StrategyCreationMatch]()

  def add(f: StrategyCreationMatch): Unit = {
    registry.add(f)
  }

  override def create(resourceType: Coding): Binding = {
    registry.foreach(f => {
      var strategy = f(resourceType)
      if (strategy.isDefined) {
        strategy
      }
    })
    throw new Exception(s"Unable to resolve binding strategy for identifier ${resourceType.toString()}")
  }
}
