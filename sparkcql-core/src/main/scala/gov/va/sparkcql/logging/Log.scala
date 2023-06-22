package gov.va.sparkcql.logging

import org.slf4j.LoggerFactory
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.logging.slf4j.Log4jLogger
import org.apache.spark.sql.{Dataset, Row}
import java.io.ByteArrayOutputStream
import org.apache.logging.log4j.{Level, LogManager}
import gov.va.sparkcql.io.ConsoleColors

class Log {}

object Log {
  var outputToConsole: Boolean = true
    
  val logger = LoggerFactory.getLogger(classOf[Log])

  def error(msg: String): Unit = {
    if (logger.isErrorEnabled()) {
      logger.error(msg)
      if (outputToConsole) {
        System.err.println(ConsoleColors.BRIGHT_RED + "ERROR: " + msg + ConsoleColors.RESET)
      }
    }
  }

  def warn(msg: String): Unit = {
    if (logger.isWarnEnabled()) {
      logger.warn(msg)
      if (outputToConsole) {
        System.err.println(ConsoleColors.BRIGHT_YELLOW + "WARNING: " + msg + ConsoleColors.RESET)
      }
    }
  }

  def info(msg: String): Unit = {
    if (logger.isInfoEnabled()) {
      logger.info(msg)
      if (outputToConsole) {
        System.err.println(ConsoleColors.BRIGHT_WHITE + msg + ConsoleColors.RESET)
      }
    }
  }

  def info[T](df: Dataset[T]): Unit = {
    info(capture(df))
  }

  def debug(msg: String): Unit = {
    if (logger.isDebugEnabled()) {
      logger.info(msg)
      if (outputToConsole) {
        System.err.println(ConsoleColors.BRIGHT_WHITE + msg + ConsoleColors.RESET)
      }
    }
  }

  def debug[T](df: Dataset[T]): Unit = {
    debug(capture(df))
  }

  protected def capture[T](df: Dataset[T]): String = {
    val outCapture = new ByteArrayOutputStream
    Console.withOut(outCapture) {
      df.show()
    }
    new String(outCapture.toByteArray)
  }
}