package gov.va.sparkcql.configuration;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Configuration extends Serializable {

    public Optional<String> readSetting(String key);

    public String readSetting(String key, String defaultValue);

    public Map<String, String> readAllSettings();

    public void writeSetting(String key, String value);

    public <I> List<Class<I>> readBinding(Class<I> interfaceClass);

    public <I> List<Class<I>> readBinding(Class<I> interfaceClass, Class<? extends I> defaultImplementationClass);

    public <I> void writeBinding(Class<I> interfaceClass, Class<? extends I> implementationClass);

    public <I> void writeBinding(Class<I> interfaceClass, List<Class<? extends I>> implementationClasses);
}
