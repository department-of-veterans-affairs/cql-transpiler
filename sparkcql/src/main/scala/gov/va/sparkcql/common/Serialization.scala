package gov.va.sparkcql.common

import org.json4s._
import org.json4s.native.JsonMethods.{parse, compact}
import org.json4s.native.Serialization.write

object Serialization {
  implicit class JsonDeserializer(val s: String) {
    def deserializeJson[T]()(implicit m: scala.reflect.Manifest[T]): T = {
      implicit val formats = DefaultFormats
      parse(s).extract(formats, m)
    }
  }

  implicit class JsonSerializer(val o: Object) {
    def serializeJson(): String = {
      implicit val formats = DefaultFormats
      native.Serialization.write(o)
    }
  }
}