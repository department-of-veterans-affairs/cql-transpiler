package gov.va.sparkcql.common;

import java.time.Duration;

final public class Formats {
	private Formats() {
	}

    public static String format(Duration duration) {
        long HH = duration.toHours();
        long MM = duration.toMinutesPart();
        long SS = duration.toSecondsPart();
        long MS = duration.toMillisPart();
        return String.format("%02dh:%02dm:%02ds:%02dms", HH, MM, SS, MS);
	}
}