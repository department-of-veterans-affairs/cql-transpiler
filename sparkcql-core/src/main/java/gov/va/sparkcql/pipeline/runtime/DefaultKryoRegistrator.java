package gov.va.sparkcql.pipeline.runtime;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.twitter.chill.AllScalaRegistrar;
import com.twitter.chill.java.PackageRegistrar;
import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.model.KryoRegistrar;
import org.apache.spark.serializer.KryoRegistrator;

import java.util.*;

public class DefaultKryoRegistrator implements KryoRegistrator {

    @Override
    public void registerClasses(Kryo kryo) {
        // Injection requires configuration but since Spark is calling us we
        // can't control the context, so we must use a default configuration.
        Configuration configuration = new EnvironmentConfiguration();

        // Apply External Registrars
        new AllScalaRegistrar().apply(kryo);
        PackageRegistrar.all().apply(kryo);

        // Register native classes defined in Core.
        kryo.register(Plan.class);
        kryo.register(EvaluatedContext.class, new JavaSerializer());

        // Register provided classes.
        var injector = new Injector(configuration);
        var registrars = injector.getInstances(KryoRegistrar.class);
        registrars.forEach(r -> r.registerClasses(kryo));
    }
}