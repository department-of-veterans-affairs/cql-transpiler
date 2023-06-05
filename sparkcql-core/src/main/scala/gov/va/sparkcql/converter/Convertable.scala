package gov.va.sparkcql.converter

trait Convertable[S, T] {
  def convert(source: S): T
}