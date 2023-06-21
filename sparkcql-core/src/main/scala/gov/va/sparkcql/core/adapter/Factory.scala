package gov.va.sparkcql.core.adapter

trait Factory {

  def isDefaultConfigurable: Boolean

  def defaultConfiguration: Option[Configuration]
}