// Generated by <a href="http://scalaxb.org/">scalaxb</a>.


/** The CqlToElmBase type defines the abstract base type for all annotation elements in the CQL Translator.
*/
trait CqlToElmBase {
  
}


case class Annotation(t: Seq[Tag] = Nil,
  s: Option[Narrative] = None,
  locator: Option[Locatorable] = None) extends CqlToElmBase
      
      


case class Tag(attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) {
  lazy val name = attributes.get("@name") map { _.as[String]}
  lazy val valueAttribute = attributes.get("@value") map { _.as[String]}
}

      
      


trait Locatorable extends CqlToElmBase {
  def librarySystem: Option[String]
  def libraryId: Option[String]
  def libraryVersion: Option[String]
  def startLine: Option[Int]
  def startChar: Option[Int]
  def endLine: Option[Int]
  def endChar: Option[Int]
}


case class Locator(attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Locatorable {
  lazy val librarySystem = attributes.get("@librarySystem") map { _.as[String]}
  lazy val libraryId = attributes.get("@libraryId") map { _.as[String]}
  lazy val libraryVersion = attributes.get("@libraryVersion") map { _.as[String]}
  lazy val startLine = attributes.get("@startLine") map { _.as[Int]}
  lazy val startChar = attributes.get("@startChar") map { _.as[Int]}
  lazy val endLine = attributes.get("@endLine") map { _.as[Int]}
  lazy val endChar = attributes.get("@endChar") map { _.as[Int]}
}

      
      


case class Narrative(mixed: Seq[scalaxb.DataRecord[Any]] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) {
  lazy val r = attributes.get("@r") map { _.as[String]}
}

      
      

sealed trait ErrorSeverity

object ErrorSeverity {
  def fromString(value: String, scope: scala.xml.NamespaceBinding)(implicit fmt: scalaxb.XMLFormat[ErrorSeverity]): ErrorSeverity = fmt.reads(scala.xml.Text(value), Nil) match {
    case Right(x: ErrorSeverity) => x
    case x => throw new RuntimeException(s"fromString returned unexpected value $x for input $value")
  }
  lazy val values: Seq[ErrorSeverity] = Seq(Info, Warning, Error)
}

case object Info extends ErrorSeverity { override def toString = "info" }
case object Warning extends ErrorSeverity { override def toString = "warning" }
case object Error extends ErrorSeverity { override def toString = "error" }

sealed trait ErrorType

object ErrorType {
  def fromString(value: String, scope: scala.xml.NamespaceBinding)(implicit fmt: scalaxb.XMLFormat[ErrorType]): ErrorType = fmt.reads(scala.xml.Text(value), Nil) match {
    case Right(x: ErrorType) => x
    case x => throw new RuntimeException(s"fromString returned unexpected value $x for input $value")
  }
  lazy val values: Seq[ErrorType] = Seq(Environment, Syntax, Include, Semantic, Internal)
}

case object Environment extends ErrorType { override def toString = "environment" }
case object Syntax extends ErrorType { override def toString = "syntax" }
case object Include extends ErrorType { override def toString = "include" }
case object Semantic extends ErrorType { override def toString = "semantic" }
case object Internal extends ErrorType { override def toString = "internal" }


/** Represents CQL to ELM conversion errors
*/
case class CqlToElmError(attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends Locatorable {
  lazy val librarySystem = attributes.get("@librarySystem") map { _.as[String]}
  lazy val libraryId = attributes.get("@libraryId") map { _.as[String]}
  lazy val libraryVersion = attributes.get("@libraryVersion") map { _.as[String]}
  lazy val startLine = attributes.get("@startLine") map { _.as[Int]}
  lazy val startChar = attributes.get("@startChar") map { _.as[Int]}
  lazy val endLine = attributes.get("@endLine") map { _.as[Int]}
  lazy val endChar = attributes.get("@endChar") map { _.as[Int]}
  lazy val message = attributes("@message").as[String]
  lazy val errorType = attributes("@errorType").as[ErrorType]
  lazy val errorSeverity = attributes.get("@errorSeverity") map { _.as[ErrorSeverity]}
  lazy val targetIncludeLibrarySystem = attributes.get("@targetIncludeLibrarySystem") map { _.as[String]}
  lazy val targetIncludeLibraryId = attributes.get("@targetIncludeLibraryId") map { _.as[String]}
  lazy val targetIncludeLibraryVersionId = attributes.get("@targetIncludeLibraryVersionId") map { _.as[String]}
}

      
      


case class CqlToElmInfo(attributes: Map[String, scalaxb.DataRecord[Any]] = Map.empty) extends CqlToElmBase {
  lazy val translatorVersion = attributes.get("@translatorVersion") map { _.as[String]}
  lazy val translatorOptions = attributes.get("@translatorOptions") map { _.as[String]}
}

      
      

