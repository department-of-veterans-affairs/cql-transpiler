package gov.va.sparkcql.common.di;

import gov.va.sparkcql.common.configuration.Configuration;

public interface NullaryFactory<T> extends Factory<T> {
    T create();
    T create(Configuration cfg);
}
