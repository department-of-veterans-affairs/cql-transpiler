package gov.va.sparkcql.compiler

case class CqlCompilation(libraries: Seq[org.hl7.elm.r1.Library], errors: Seq[String])