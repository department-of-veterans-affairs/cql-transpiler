// package gov.va.sparkcql.core.model

// import org.hl7.elm.r1

// package object xsd {
//   case class QName(uri: String, name: String) {
//     override def toString(): String = {
//       s"${uri}/${name}"
//     }
//   }

//   object QName {
//     def apply(qname: javax.xml.namespace.QName): QName = QName(qname.getNamespaceURI(), qname.getLocalPart())
//   }
// }