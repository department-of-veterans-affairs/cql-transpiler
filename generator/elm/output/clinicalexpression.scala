// Generated by <a href="http://scalaxb.org/">scalaxb</a>.


/** 
			This file defines the expression extensions that introduce clinically relevant dependencies such as clinical data access, terminology, and value set considerations.
		
*/


/** The CodeFilterElement type specifies a terminology filter criteria for use within a retrieve, specified as either [property] [comparator] [value] or [search] [comparator] [value].
*/
case class CodeFilterElement(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  value: Expression,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val property = attributes.get("@property") map { _.as[String]}
  lazy val valueSetProperty = attributes.get("@valueSetProperty") map { _.as[String]}
  lazy val search = attributes.get("@search") map { _.as[String]}
  lazy val comparator = attributes("@comparator").as[String]
}

      
      


/** The DateFilterElement type specifies a date-valued filter criteria for use within a retrieve, specified as either a date-valued [property], a date-value [lowProperty] and [highProperty] or a [search], and an expression that evaluates to a date or time type, an interval of a date or time type, or a time-valued Quantity.
*/
case class DateFilterElement(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  value: Expression,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val property = attributes.get("@property") map { _.as[String]}
  lazy val lowProperty = attributes.get("@lowProperty") map { _.as[String]}
  lazy val highProperty = attributes.get("@highProperty") map { _.as[String]}
  lazy val search = attributes.get("@search") map { _.as[String]}
}

      
      


/** The OtherFilterElement type specifies an arbitrarily-typed filter criteria for use within a retrieve, specified as either [property] [comparator] [value] or [search] [comparator] [value].
*/
case class OtherFilterElement(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  value: Expression,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val property = attributes.get("@property") map { _.as[String]}
  lazy val search = attributes.get("@search") map { _.as[String]}
  lazy val comparator = attributes("@comparator").as[String]
}

      
      


/** The IncludeElement type specifies include information for an include within a retrieve.
*/
case class IncludeElement(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val includeFrom = attributes.get("@includeFrom") map { _.as[String]}
  lazy val relatedDataType = attributes("@relatedDataType").as[javax.xml.namespace.QName]
  lazy val relatedProperty = attributes.get("@relatedProperty") map { _.as[String]}
  lazy val relatedSearch = attributes.get("@relatedSearch") map { _.as[String]}
  lazy val isReverse = attributes.get("@isReverse") map { _.as[Boolean]}
}

      
      


/** The retrieve expression defines clinical data that will be used by the artifact. This expression allows clinically relevant filtering criteria to be provided in a well-defined and computable way. This operation defines the integration boundary for artifacts. The result of a retrieve is defined to return the same data for subsequent invocations within the same evaluation request. This means in particular that patient data updates made during the evaluation request are not visible to the artifact. In effect, the patient data is a snapshot of the data as of the start of the evaluation. This ensures strict deterministic and functional behavior of the artifact, and allows the implementation engine freedom to cache intermediate results in order to improve performance.
*/
case class Retrieve(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  id: Option[Expression] = None,
  codes: Option[Expression] = None,
  dateRange: Option[Expression] = None,
  context: Option[Expression] = None,
  include: Seq[IncludeElement] = Nil,
  codeFilter: Seq[CodeFilterElement] = Nil,
  dateFilter: Seq[DateFilterElement] = Nil,
  otherFilter: Seq[OtherFilterElement] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Expression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val dataType = attributes("@dataType").as[javax.xml.namespace.QName]
  lazy val templateId = attributes.get("@templateId") map { _.as[String]}
  lazy val idProperty = attributes.get("@idProperty") map { _.as[String]}
  lazy val idSearch = attributes.get("@idSearch") map { _.as[String]}
  lazy val contextProperty = attributes.get("@contextProperty") map { _.as[String]}
  lazy val contextSearch = attributes.get("@contextSearch") map { _.as[String]}
  lazy val codeProperty = attributes.get("@codeProperty") map { _.as[String]}
  lazy val codeSearch = attributes.get("@codeSearch") map { _.as[String]}
  lazy val codeComparator = attributes.get("@codeComparator") map { _.as[String]}
  lazy val valueSetProperty = attributes.get("@valueSetProperty") map { _.as[String]}
  lazy val dateProperty = attributes.get("@dateProperty") map { _.as[String]}
  lazy val dateLowProperty = attributes.get("@dateLowProperty") map { _.as[String]}
  lazy val dateHighProperty = attributes.get("@dateHighProperty") map { _.as[String]}
  lazy val dateSearch = attributes.get("@dateSearch") map { _.as[String]}
  lazy val includedIn = attributes.get("@includedIn") map { _.as[String]}
}

      
      


/** The Search operation provides an operator that returns the result of an indexing expression on an instance. It is effectively the same as a property access, but uses the name of a defined search on the type, rather than the name of a property on the class.
*/
case class Search(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  source: Option[Expression] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Propertyable {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val path = attributes("@path").as[String]
  lazy val scope = attributes.get("@scope") map { _.as[String]}
}

      
      


/** The CodeSystemDef type defines a code system identifier that can then be used to identify code systems involved in value set definitions.
*/
case class CodeSystemDef(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val name = attributes("@name").as[String]
  lazy val id = attributes("@id").as[String]
  lazy val version = attributes.get("@version") map { _.as[String]}
  lazy val accessLevel = attributes("@accessLevel").as[AccessModifier]
}

      
      


/** The ValueSetDef type defines a value set identifier that can be referenced by name anywhere within an expression.

The id specifies the globally unique identifier for the value set. This may be an HL7 OID, a FHIR URL, or a CTS2 value set URL.

If version is specified, it will be used to resolve the version of the value set definition to be used. Otherwise, the most current published version of the value set is assumed.

If codeSystems are specified, they will be used to resolve the code systems used within the value set definition to construct the expansion set.
Note that the recommended approach to statically binding to an expansion set is to use a value set definition that specifies the version of each code system used. The codeSystemVersions attribute is provided only to ensure static binding can be achieved when the value set definition does not specify code system versions as part of the definition header.			
*/
case class ValueSetDef(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  codeSystem: Seq[CodeSystemRef] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val name = attributes.get("@name") map { _.as[String]}
  lazy val id = attributes("@id").as[String]
  lazy val version = attributes.get("@version") map { _.as[String]}
  lazy val accessLevel = attributes("@accessLevel").as[AccessModifier]
}

      
      


/** The CodeDef type defines a code identifier that can then be used to reference single codes anywhere within an expression.
*/
case class CodeDef(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  codeSystem: Option[CodeSystemRef] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val name = attributes("@name").as[String]
  lazy val id = attributes("@id").as[String]
  lazy val display = attributes.get("@display") map { _.as[String]}
  lazy val accessLevel = attributes("@accessLevel").as[AccessModifier]
}

      
      


/** The ConceptDef type defines a concept identifier that can then be used to reference single concepts anywhere within an expression.
*/
case class ConceptDef(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  code: Seq[CodeRef] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Element {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val name = attributes("@name").as[String]
  lazy val display = attributes.get("@display") map { _.as[String]}
  lazy val accessLevel = attributes("@accessLevel").as[AccessModifier]
}

      
      


/** The CodeSystemRef expression allows a previously defined named code system to be referenced within an expression. Conceptually, referencing a code system returns the set of codes in the code system. Note that this operation should almost never be performed in practice. Code system references are allowed in order to allow for testing of code membership in a particular code system.
*/
case class CodeSystemRef(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Expression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val name = attributes.get("@name") map { _.as[String]}
  lazy val libraryName = attributes.get("@libraryName") map { _.as[String]}
}

      
      


/** The ValueSetRef expression allows a previously defined named value set to be referenced within an expression. Conceptually, referencing a value set returns the expansion set for the value set as a list of codes.
*/
case class ValueSetRef(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Expression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val name = attributes.get("@name") map { _.as[String]}
  lazy val libraryName = attributes.get("@libraryName") map { _.as[String]}
  lazy val preserve = attributes.get("@preserve") map { _.as[Boolean]}
}

      
      


/** The CodeRef expression allows a previously defined code to be referenced within an expression.
*/
case class CodeRef(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Expression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val name = attributes.get("@name") map { _.as[String]}
  lazy val libraryName = attributes.get("@libraryName") map { _.as[String]}
}

      
      


/** The ConceptRef expression allows a previously defined concept to be referenced within an expression.
*/
case class ConceptRef(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Expression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val name = attributes.get("@name") map { _.as[String]}
  lazy val libraryName = attributes.get("@libraryName") map { _.as[String]}
}

      
      


/** The Code type represents a literal code selector.
*/
case class Code(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  system: CodeSystemRef,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Expression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val code = attributes("@code").as[String]
  lazy val display = attributes.get("@display") map { _.as[String]}
}

      
      


/** The Concept type represents a literal concept selector.
*/
case class Concept(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  code: Seq[Code] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Expression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val display = attributes.get("@display") map { _.as[String]}
}

      
      


/** The InCodeSystem operator returns true if the given code is in the given code system.

The first argument is expected to be a String, Code, or Concept.

The second argument is expected to be of type CodeSystem. When this argument is statically a CodeSystemRef, this allows for both static analysis of the code system references within an artifact, as well as the implementation of code system membership by the target environment as a service call to a terminology server, if desired.
*/
case class InCodeSystem(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  signature: Seq[TypeSpecifier] = Nil,
  code: Expression,
  codesystem: Option[CodeSystemRef] = None,
  codesystemExpression: Option[Expression] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends OperatorExpression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
}

      
      


/** The AnyInCodeSystem operator returns true if any of the given codes are in the given code system.

The first argument is expected to be a list of String, Code, or Concept.

The second argument is expected to be of type CodeSystem. When this argument is statically a CodeSystemRef, this allows for both static analysis of the code system references within an artifact, as well as the implementation of code system membership by the target environment as a service call to a terminology server, if desired.
*/
case class AnyInCodeSystem(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  signature: Seq[TypeSpecifier] = Nil,
  codes: Expression,
  codesystem: Option[CodeSystemRef] = None,
  codesystemExpression: Option[Expression] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends OperatorExpression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
}

      
      


/** The InValueSet operator returns true if the given code is in the given value set.

The first argument is expected to be a String, Code, or Concept.

The second argument is expected to be of type ValueSet. When this argument is statically a ValueSetRef, this allows for both static analysis of the value set references within an artifact, as well as the implementation of valueset membership by the target environment as a service call to a terminology server, if desired.
*/
case class InValueSet(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  signature: Seq[TypeSpecifier] = Nil,
  code: Expression,
  valueset: Option[ValueSetRef] = None,
  valuesetExpression: Option[Expression] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends OperatorExpression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
}

      
      


/** The AnyInValueSet operator returns true if any of the given codes are in the given value set.

The first argument is expected to be a list of String, Code, or Concept.

The second argument is expected to be of type ValueSet. When this argument is statically a ValueSetRef, this allows for both static analysis of the value set references within an artifact, as well as the implementation of valueset membership by the target environment as a service call to a terminology server, if desired.
*/
case class AnyInValueSet(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  signature: Seq[TypeSpecifier] = Nil,
  codes: Expression,
  valueset: Option[ValueSetRef] = None,
  valuesetExpression: Option[Expression] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends OperatorExpression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
}

      
      


/** The ExpandValueSet operator returns the current expansion for the given value set.

The operation exoects a single argument of type ValueSet. This may be a static reference to a value set (i.e. a ValueSetRef), or a ValueSet value to support dynamic value set usage. The operation is used as the implicit conversion from a ValueSet reference to a list of codes.

If the argument is null, the result is null.
*/
case class ExpandValueSet(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  signature: Seq[TypeSpecifier] = Nil,
  operand: Expression,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends UnaryExpression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
}

      
      


/** The Subsumes operator returns true if the given codes are equivalent, or if the first code subsumes the second (i.e. the first code is an ancestor of the second in a subsumption hierarchy), and false otherwise.

For the Concept overload, this operator returns true if any code in the first concept subsumes any code in the second.

If either or both arguments are null, the result is null.
*/
case class Subsumes(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  signature: Seq[TypeSpecifier] = Nil,
  operand: Seq[Expression] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends BinaryExpression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
}

      
      


/** The SubsumedBy operator returns true if the given codes are equivalent, or if the first code is subsumed by the second code (i.e. the first code is a descendent of the second code in a subsumption hierarchy), and false otherwise.

For the Concept overload, this operator returns true if any code in the first concept is subsumed by any code in the second.

If either or both arguments are null, the result is null.
*/
case class SubsumedBy(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  signature: Seq[TypeSpecifier] = Nil,
  operand: Seq[Expression] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends BinaryExpression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
}

      
      


/** The Quantity type defines a clinical quantity. For example, the quantity 10 days or 30 mmHg. The value is a decimal, while the unit is expected to be a valid UCUM unit or calendar duration keyword, singular or plural.
*/
case class Quantity(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Expression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val valueAttribute = attributes.get("@value") map { _.as[BigDecimal]}
  lazy val unit = attributes.get("@unit") map { _.as[String]}
}

      
      


/** The Ratio type defines a ratio between two quantities. For example, the titre 1:128, or the concentration ratio 5 mg/10 mL. The numerator and denominator are both quantities.
*/
case class Ratio(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  numerator: Quantity,
  denominator: Quantity,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Expression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
}

      
      


/** Calculates the age in the specified precision of a person born on the given date.

The CalculateAge operator is defined for Date and DateTime.

For the Date overload, the calculation is performed using Today(), the precision must be one of year, month, week, or day, and the result is the number of whole calendar periods that have elapsed between the given date and today.

For the DateTime overload, the calculation is performed using Now(), and the result is the number of whole calendar periods that have elapsed between the given datetime and now.
*/
case class CalculateAge(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  signature: Seq[TypeSpecifier] = Nil,
  operand: Expression,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends UnaryExpression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val precision = attributes.get("@precision") map { _.as[DateTimePrecision]}
}

      
      


/** Calculates the age in the specified precision of a person born on a given date, as of another given date.

The CalculateAgeAt operator has two signatures:
  (Date, Date)
  (DateTime, DateTime)

For the Date overload, precision must be one of year, month, week, or day, and the result is the number of whole calendar periods that have elapsed between the first date and the second date.

For the DateTime overload, the result is the number of whole calendar periods that have elapsed between the first datetime and the second datetime.
*/
case class CalculateAgeAt(annotation: Seq[CqlToElmBase] = Nil,
  resultTypeSpecifier: Option[TypeSpecifier] = None,
  signature: Seq[TypeSpecifier] = Nil,
  operand: Seq[Expression] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends BinaryExpression {
  lazy val localId = attributes.get("@localId") map { _.as[String]}
  lazy val locator = attributes.get("@locator") map { _.as[String]}
  lazy val resultTypeName = attributes.get("@resultTypeName") map { _.as[javax.xml.namespace.QName]}
  lazy val precision = attributes.get("@precision") map { _.as[DateTimePrecision]}
}

      
      

