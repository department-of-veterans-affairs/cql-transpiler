package gov.va.sparkcql.model.elm

/**
  * https://cql.hl7.org/elm/schema/library.xsd
  */
trait Library

/**
  * VersionedIdentifier is composed of three parts: (1) an optional system, or
  * namespace, which provides a globally unique, stable scope for the identifier,
  * (2) an identifier which identifies the set of all versions of a given resource, and
  * (3) the actual version of the instance of interest in this set. The VersionedIdentifier
  * therefore points to an individual 'versioned' instance of a resource such as the third
  * version of a library.
  */
class VersionedIdentifier(val id: String, val system: Option[String], val version: Option[String])

class VersionedIdentifier2(id: String, system: Option[String], version: Option[String]) extends VersionedIdentifier(id, system, version)

// object VersionedIdentifier {
//   def apply(id: String, system: Option[String], version: Option[String]) = {
//     new VersionedIdentifier(id, system, version)
//   }

//   def unapply(value: VersionedIdentifier): Option[(String, Option[String], Option[String])] = {
//     Some((value.id, value.system, value.version))
//   }
// }

/** Defines a data model that is available within the artifact.
*/

// case class UsingDef(annotation: Seq[CqlToElmBase] = Nil,
//   resultTypeSpecifier: Option[TypeSpecifier] = None,
//   attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
//   lazy val localId = attributes.get("@localId") map { _.as[String]}
//   lazy val locator = attributes.get("@locator") map { _.as[String]}
//   lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
//   lazy val localIdentifier = attributes("@localIdentifier").as[String]
//   lazy val uri = attributes("@uri").as[java.net.URI]
//   lazy val version = attributes.get("@version") map { _.as[String]}
// }

      
      


// /** Includes a library for use within the artifact.
// */
// case class IncludeDef(annotation: Seq[CqlToElmBase] = Nil,
//   resultTypeSpecifier: Option[TypeSpecifier] = None,
//   attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
//   lazy val localId = attributes.get("@localId") map { _.as[String]}
//   lazy val locator = attributes.get("@locator") map { _.as[String]}
//   lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
//   lazy val localIdentifier = attributes("@localIdentifier").as[String]
//   lazy val mediaType = attributes("@mediaType").as[java.net.URI]
//   lazy val path = attributes("@path").as[java.net.URI]
//   lazy val version = attributes.get("@version") map { _.as[String]}
// }

      
      


// /** The ContextDef type defines a context definition statement. Note that this is a placeholder for the context statement within the library. The effect of the context definition is applied by the translator to the definitions that follow.
// */
// case class ContextDef(annotation: Seq[CqlToElmBase] = Nil,
//   resultTypeSpecifier: Option[TypeSpecifier] = None,
//   attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
//   lazy val localId = attributes.get("@localId") map { _.as[String]}
//   lazy val locator = attributes.get("@locator") map { _.as[String]}
//   lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
//   lazy val name = attributes.get("@name") map { _.as[String]}
// }

      
      


// case class Usings(defValue: Seq[UsingDef] = Nil)
      
      


// case class Includes(defValue: Seq[IncludeDef] = Nil)
      
      


// case class Parameters(defValue: Seq[ParameterDef] = Nil)
      
      


// case class CodeSystems(defValue: Seq[CodeSystemDef] = Nil)
      
      


// case class ValueSets(defValue: Seq[ValueSetDef] = Nil)
      
      


// case class Codes(defValue: Seq[CodeDef] = Nil)
      
      


// case class Concepts(defValue: Seq[ConceptDef] = Nil)
      
      


// case class Contexts(defValue: Seq[ContextDef] = Nil)
      
      


// case class Statements(defValue: Seq[ExpressionDefable] = Nil)
      
      


// /** A Library is an instance of a CQL-ELM library. 
// */
// case class Library(annotation: Seq[CqlToElmBase] = Nil,
//   resultTypeSpecifier: Option[TypeSpecifier] = None,
//   identifier: VersionedIdentifier,
//   schemaIdentifier: VersionedIdentifier,
//   usings: Option[Usings] = None,
//   includes: Option[Includes] = None,
//   parameters: Option[Parameters] = None,
//   codeSystems: Option[CodeSystems] = None,
//   valueSets: Option[ValueSets] = None,
//   codes: Option[Codes] = None,
//   concepts: Option[Concepts] = None,
//   contexts: Option[Contexts] = None,
//   statements: Option[Statements] = None,
//   attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
//   lazy val localId = attributes.get("@localId") map { _.as[String]}
//   lazy val locator = attributes.get("@locator") map { _.as[String]}
//   lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
// }