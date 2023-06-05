package gov.va.sparkcql

object functions {
  case class define(name: String, library: Option[String] = None)
}

class DefineMap