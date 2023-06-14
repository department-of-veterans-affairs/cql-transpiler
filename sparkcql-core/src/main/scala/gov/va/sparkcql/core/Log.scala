package gov.va.sparkcql.core

import org.slf4j.LoggerFactory
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.logging.slf4j.Log4jLogger
import org.apache.spark.sql.{Dataset, Row}
import java.io.ByteArrayOutputStream

class Log {}

object Log {
  var outputToConsole: Boolean = true
    
  val logger = LoggerFactory.getLogger(classOf[Log])

  def error(msg: String): Unit = {
    logger.error(msg)
    if (outputToConsole) {
      System.err.println(ConsoleColors.BRIGHT_RED + "ERROR: " + msg + ConsoleColors.RESET)
    }
  }

  def warn(msg: String): Unit = {
    logger.warn(msg)
    if (outputToConsole) {
      System.err.println(ConsoleColors.BRIGHT_YELLOW + "WARNING: " + msg + ConsoleColors.RESET)
    }
  }

  def info(msg: String): Unit = {
    logger.info(msg)
    if (outputToConsole) {
      System.err.println(ConsoleColors.BRIGHT_WHITE + msg + ConsoleColors.RESET)
    }
  }

  def info[T](df: Dataset[T]): Unit = {
    val outCapture = new ByteArrayOutputStream
    Console.withOut(outCapture) {
      df.show()
    }
    val result = new String(outCapture.toByteArray)
    info(result)
  }
}