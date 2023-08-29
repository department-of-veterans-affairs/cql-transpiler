package gov.va.sparkcql.configuration;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Configuration extends Serializable {

    public Optional<String> readSetting(String key);

    public String readSetting(String key, String defaultValue);

    public void writeSetting(String key, String value);

    public <I, T> Class<T> readBinding(Class<I> interfaceClass);

    public <I, T> void writeBinding(Class<I> interfaceClass, Class<T> implementationClass);

    public <I, T> void writeBinding(Class<I> interfaceClass, List<Class<T>> implementationClasses);
}
