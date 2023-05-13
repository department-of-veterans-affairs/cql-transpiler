package gov.va.sparkcql.common;

import java.time.Duration;
import java.time.Instant;

public class Stopwatch {

    Instant start;
    Instant end;

    public Stopwatch() {
        try {
            start = Instant.now();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print() {
        System.out.println(Formats.format(this.lapse()));
    }
    
    public void print(String title) {
        System.out.println(title + ": " + Formats.format(this.lapse()));
    }

    void stop() {
        end = Instant.now();
    }

    public Duration lapse() {
        if (end == null) {
            return Duration.between(start, Instant.now());
        } else {
            return Duration.between(start, end);
        }
    }
}