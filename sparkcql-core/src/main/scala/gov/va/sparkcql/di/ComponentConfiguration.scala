package gov.va.sparkcql.di

import scala.collection.mutable.HashMap

class ComponentConfiguration(sessionConfiguration: Map[String, String]) {
  
  val localConfiguration = HashMap[String, String]()

  def this() = this(Map())
  
  def read(key: String): String = read(key, null)

  def read(key: String, defaultValue: String): String = {
    localConfiguration.getOrElse(key, {
      sessionConfiguration.getOrElse(key, {
        Option(System.getProperty(key)).getOrElse({
            var envVarFriendlyName = key.replace('.', '_').toUpperCase()
            Option(System.getenv(envVarFriendlyName)).getOrElse(
              defaultValue
            )
        })
      })
    })
  }

  def write(key: String, value: String) = localConfiguration.put(key, value)

  def write(componentConfiguration: ComponentConfiguration) = {
    this.localConfiguration ++= componentConfiguration.localConfiguration
  }
}