// package gov.va.sparkcql.core.translation.elm2spark

// import org.apache.spark.sql.functions._
// import org.apache.spark.sql.{Column}
// import gov.va.sparkcql.core.model.elm.ElmTypes
// import scala.util.Try

// object ElmTypeConversion {
  
//   def convert(qname: javax.xml.namespace.QName, value: String): Column = {
//     val qn = QName(qname)
//     qn match {
//       case QName("urn:hl7-org:elm-types:r1", "Integer") => lit(value.toInt)
//       case QName("urn:hl7-org:elm-types:r1", "Boolean") => lit(value.toBoolean)
//       case QName("urn:hl7-org:elm-types:r1", "Long") => lit(value.toLong)
//       case QName("urn:hl7-org:elm-types:r1", "Decimal") => lit(value.toDouble)
//       case QName("urn:hl7-org:elm-types:r1", "String") => lit(value)
//     }
//   }

//   def convert(elmType: Object): Column = {
//     elmType match {
//       case ElmTypes.DateInterval(low, high, lowClosed, highClosed) => 
//         struct(lit(low.value).alias("low"), lit(high.value).alias("high"))
//       case ElmTypes.DateTimeInterval(low, high, lowClosed, highClosed) => 
//         struct(lit(low.value).alias("low"), lit(high.value).alias("high"))
//     }
//   }  
// }