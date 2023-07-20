package gov.va.sparkcql.common;

import org.apache.spark.sql.Dataset;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Log {
  
    public static Boolean forceToConsole = true;
    public static Logger logger = LoggerFactory.getLogger(Log.class);

    public static void error(String msg) {
        logger.error(msg);
        if (forceToConsole) {
            System.err.println(ConsoleColors.BRIGHT_RED + "ERROR: " + msg + ConsoleColors.RESET);
        }
    }

    public static void warn(String msg) {
        logger.warn(msg);
        if (forceToConsole) {
            System.err.println(ConsoleColors.BRIGHT_YELLOW + "WARNING: " + msg + ConsoleColors.RESET);
        }
    }

    public static void info(String msg) {
        logger.info(msg);
        if (forceToConsole) {
            System.err.println(ConsoleColors.BRIGHT_WHITE + msg + ConsoleColors.RESET);
        }
    }

    public static void info(Dataset<?> df) {
        info(capture(df));
    }

    public static void debug(String msg) {
        logger.info(msg);
        if (forceToConsole) {
            System.err.println(ConsoleColors.BRIGHT_WHITE + msg + ConsoleColors.RESET);
        }
    }

    public static void debug(Dataset<?> ds) {
        debug(capture(ds));
    }

    protected static String capture(Dataset<?> ds) {
        if (ds != null) {
            PrintStream oldStream = System.out;
            try {
                var outStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(outStream);
                System.setOut(printStream);
                ds.show();
                System.out.flush();
                return outStream.toString();
            } finally {
                System.setOut(oldStream);
            }
        } else {
            return "DataFrame is empty";
        }
    }
}