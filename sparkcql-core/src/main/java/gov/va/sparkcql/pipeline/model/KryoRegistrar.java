package gov.va.sparkcql.pipeline.model;

import com.esotericsoftware.kryo.Kryo;

public interface KryoRegistrar {

    public void registerClasses(Kryo kryo);
}