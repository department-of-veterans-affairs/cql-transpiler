package gov.va.sparkcql.pipeline.model;

import com.esotericsoftware.kryo.Kryo;

public class CqfKryoRegistrar implements KryoRegistrar {

    @Override
    public void registerClasses(Kryo kryo) {
        kryo.register(org.opencds.cqf.cql.engine.runtime.DateTime.class);
    }
}