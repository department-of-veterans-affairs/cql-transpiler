package gov.va.sparkcql.types

case class IdentifiedQuantity[T](identifier: Identifier, quantity: T)