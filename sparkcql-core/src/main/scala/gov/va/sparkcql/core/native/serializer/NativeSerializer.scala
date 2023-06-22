// package gov.va.sparkcql.core.adapter.model

// import scala.reflect.runtime.universe._
// import org.apache.spark.sql.types.StructType
// import gov.va.sparkcql.core.model.CqlContent
// import org.apache.spark.sql.Encoders
// import gov.va.sparkcql.core.model.ValueSet
// import gov.va.sparkcql.core.translator.cql2elm.CqlCompilerGateway
// import gov.va.sparkcql.core.model.VersionedId

// class CanonicalModelAdapter extends DataAdapter {

//   override def deserialize[T : TypeTag](data: String): Option[T] = {
//     typeOf[T] match {
//       case x if typeOf[T] <:< typeOf[CqlContent] =>
//         val id = VersionedId(CqlCompilerGateway.parseVersionedIdentifier(data))
//         Some(new CqlContent(id, data).asInstanceOf[T])
//       case _ => None
//     }
//   }

// }
