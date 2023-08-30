package gov.va.sparkcql.diagnostic;

import java.time.Duration;

public class Stopwatch {
  
    private Long startTime = System.nanoTime();
    private Long endTime = 0L;

    public void stop() {
        this.endTime = System.nanoTime();
    }

    public Duration getLapse() {
        if (endTime == 0L) {
            return Duration.ofNanos(System.nanoTime() - startTime);
        } else {
            return Duration.ofNanos(endTime - startTime);
        }
    }

    public String toString() {
        var duration = getLapse();
        var HH = duration.toHours();
        var MM = duration.toMinutes();
        var SS = duration.toSeconds();
        var MS = duration.toMillis();
        return String.format("%dh:%dm:%ds:%dms", HH, MM, SS, MS);
    }

    public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }

    public void print() {
        System.out.println(toString());
    }
}