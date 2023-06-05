package gov.va.sparkcql.converter

// TODO: Consider a type class to handle provider type conversions but note it must be interoperable w/ Python
abstract class Converter {
  def convert[S, T](source: S): T
}