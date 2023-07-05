package gov.va.sparkcql.io

import org.slf4j.LoggerFactory
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.logging.slf4j.Log4jLogger
import org.apache.spark.sql.{DataFrame, Dataset}
import java.io.ByteArrayOutputStream
import org.apache.logging.log4j.{Level, LogManager}

class Log {}

object Log {
  var forceToConsole: Boolean = true
    
  val logger = LoggerFactory.getLogger(classOf[Log])

  def error(msg: String): Unit = {
    logger.error(msg)
    if (forceToConsole) {
      System.err.println(ConsoleColors.BRIGHT_RED + "ERROR: " + msg + ConsoleColors.RESET)
    }
  }

  def warn(msg: String): Unit = {
    logger.warn(msg)
    if (forceToConsole) {
      System.err.println(ConsoleColors.BRIGHT_YELLOW + "WARNING: " + msg + ConsoleColors.RESET)
    }
  }

  def info(msg: String): Unit = {
    logger.info(msg)
    if (forceToConsole) {
      System.err.println(ConsoleColors.BRIGHT_WHITE + msg + ConsoleColors.RESET)
    }
  }

  def info[T](df: Dataset[_]): Unit = {
    info(capture(df))
  }

  def debug(msg: String): Unit = {
    logger.info(msg)
    if (forceToConsole) {
      System.err.println(ConsoleColors.BRIGHT_WHITE + msg + ConsoleColors.RESET)
    }
  }

  def debug[T](df: DataFrame): Unit = {
    debug(capture(df))
  }

  protected def capture[T](df: Dataset[_]): String = {
    if (df != null) {
      val outCapture = new ByteArrayOutputStream
      Console.withOut(outCapture) {
        df.show()
      }
      new String(outCapture.toByteArray)
    } else {
      "DataFrame is empty"
    }
  }
}