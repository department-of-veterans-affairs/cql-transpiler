package gov.va.sparkcql.lang;

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

public abstract class TypeReference<T> {
    private final Type type;

    protected TypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException("Type parameter missing.");
        }
    }

    public Type getType() {
        return type;
    }
}