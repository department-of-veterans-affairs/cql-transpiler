package gov.va.sparkcql.synthea

sealed trait PopulationSize

object PopulationSize {
  final case object PopulationSize10 extends PopulationSize
  final case object PopulationSize1000 extends PopulationSize
}