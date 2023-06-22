package gov.va.sparkcql.core.types

case class IdentifiedQuantity[T](identifier: Identifier, quantity: T)