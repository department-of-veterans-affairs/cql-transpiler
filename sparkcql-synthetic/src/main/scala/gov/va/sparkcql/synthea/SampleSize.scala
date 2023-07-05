package gov.va.sparkcql.synthea

sealed trait SampleSize

object SampleSize {
  final case object SampleSizeNone extends SampleSize
  final case object SampleSize10 extends SampleSize
  final case object SampleSize1000 extends SampleSize
}