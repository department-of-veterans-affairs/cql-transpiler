package gov.va.sparkcql.common.types;

import java.io.Serializable;

public abstract class Tuple<A, B> implements Serializable {

    private A a;
    private B b;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
