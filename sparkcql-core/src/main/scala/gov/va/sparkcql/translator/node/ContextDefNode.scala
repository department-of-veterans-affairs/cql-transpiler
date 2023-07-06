package gov.va.sparkcql.translator.node

import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.translator._
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._

class ContextDefNode(val element: elm.ContextDef) extends Node {

  override def translate(env: Environment): Object = {
    // val relatedContextRetrieve = parent.descendants.collect { case n: SingletonFromNode => n }
    // val retrieves = relatedContextRetrieve.map(_.element.getOperand().asInstanceOf[elm.Retrieve].getDataType())
    
    // val contextDataFrame = element.getName() match {
    //   case "Patient" => env.dataAggregate.acquire(relatedContextRetrieve.getOperand().asInstanceOf[elm.Retrieve].getDataType())
    //   case "Practitioner" => throw new UnsupportedOperationException()
    //   case "Unfiltered" => throw new UnsupportedOperationException()
    //   case _ => throw new UnsupportedOperationException()
    // }

    // contextDataFrame
    ???
  }
}