package gov.va.sparkcql.binding.factory

import gov.va.sparkcql.model.elm.VersionedIdentifier
import scala.collection.mutable.HashSet
import gov.va.sparkcql.binding.strategy.BindingStrategy

class AssembledBindingFactory extends BindingFactory {

  type StrategyCreator = (VersionedIdentifier) => Option[BindingStrategy]
  val registry = HashSet[StrategyCreator]()

  def add(f: StrategyCreator): Unit = {
    registry.add(f)
  }

  override def create(dataTypeIdentifier: VersionedIdentifier): BindingStrategy = {
    registry.foreach(f => {
      var strategy = f(dataTypeIdentifier)
      if (strategy.isDefined) {
        strategy
      }
    })
    throw new Exception(s"Unable to resolve binding strategy for identifier ${dataTypeIdentifier.toString()}")
  }
}
