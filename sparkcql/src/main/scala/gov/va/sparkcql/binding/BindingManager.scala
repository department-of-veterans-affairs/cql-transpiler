package gov.va.sparkcql.binding

import scala.reflect.runtime.universe._
import gov.va.sparkcql.session.SessionConfiguration
import scala.collection.mutable.HashMap
import gov.va.sparkcql.session.Session
import gov.va.sparkcql.model.BoundType
import org.apache.spark.sql.Dataset

class BindingManager(session: Session, configuration: SessionConfiguration) extends Binding {

  val DefaultBindingKey = "~default~"

  lazy private val bindingMap: HashMap[String, Binding] = {
    val configBindings = configuration.bindings.getOrElse(throw new Exception("No bindings configured for sparkcql."))
    val map = HashMap[String, Binding]()
    configBindings.foreach(configBinding => {
      var createMethod = Class.forName(configBinding.implementationClassName)
        .getMethod("create", classOf[Session], classOf[Option[Map[String, Any]]])
      var instance = createMethod.invoke(null, session, configBinding.settings).asInstanceOf[Binding]
      
      if (configBinding.isDefaultBinding.getOrElse(false)) {
        if (map.isDefinedAt(DefaultBindingKey)) throw new Exception("Only one binding can be set as default.")
        map(DefaultBindingKey) = instance
      } else {
        configBinding.boundTypes.foreach(_.foreach({map.put(_, instance)}))
      }
    })
    map
  }

  lazy private val defaultBinding: Option[Binding] = {
    bindingMap.get(DefaultBindingKey)
  }

  override def retrieve[T <: BoundType: TypeTag](filter: Option[List[PredicateLike]]): Option[Dataset[T]] = {
    val typeName1 = typeOf[T].typeSymbol.name
    val typeName2 = typeOf[T].typeSymbol.fullName
    val binding = bindingMap.getOrElse(typeOf[T].typeSymbol.fullName, defaultBinding.getOrElse(throw new Exception("Unable to resolve binding for ${typeName}")))
    binding.retrieve[T](filter)
  }
}