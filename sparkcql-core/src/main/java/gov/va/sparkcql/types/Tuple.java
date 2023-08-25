package gov.va.sparkcql.types;

import java.io.Serializable;

public class Tuple<A, B> implements Serializable {

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

    public static <A, B> Tuple<A, B> of(A a, B b) {
        var t = new Tuple<A, B>();
        t.setA(a);
        t.setB(b);
        return t;
    }
}
