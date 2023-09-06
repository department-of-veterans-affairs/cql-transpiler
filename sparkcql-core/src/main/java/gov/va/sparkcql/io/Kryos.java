package gov.va.sparkcql.io;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.twitter.chill.AllScalaRegistrar;
import com.twitter.chill.KryoInstantiator;
import com.twitter.chill.KryoPool;
import com.twitter.chill.java.PackageRegistrar;
import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.Plan;
import scala.Tuple2$;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public final class Kryos {

    private Kryos() {
    }

    private static Kryo kryo;

    private static Kryo getKryo() {
        if (kryo == null) {
            kryo = new Kryo();

            new AllScalaRegistrar().apply(kryo);
            PackageRegistrar.all().apply(kryo);

            kryo.register(List.class, new JavaSerializer());
            kryo.register(ArrayList.class, new JavaSerializer());
            kryo.register(HashMap.class, new JavaSerializer());
            kryo.register(Collection.class, new JavaSerializer());
            // kryo.register(org.opencds.cqf.cql.engine.runtime.DateTime, new JavaSerializer());
            kryo.register(Plan.class);
            kryo.register(EvaluatedContext.class);
        }

        return kryo;
    }

    public static <T> T read(ObjectInputStream inputStream, Class<T> clazz) {
        var input = new Input(inputStream);
        return getKryo().readObject(input, clazz);
    }

    public static <T> void write(ObjectOutputStream outputStream, T object) {
        var output = new Output(outputStream);
        getKryo().writeObject(output, object);
    }
}