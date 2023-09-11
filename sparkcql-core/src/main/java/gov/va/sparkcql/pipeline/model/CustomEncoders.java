package gov.va.sparkcql.pipeline.model;

import org.apache.spark.sql.Encoder;

public class CustomEncoders {

    private CustomEncoders() {
    }

    public static <T> Encoder<T> of(Class<T> clazz) {
        throw new UnsupportedOperationException();
    }
}