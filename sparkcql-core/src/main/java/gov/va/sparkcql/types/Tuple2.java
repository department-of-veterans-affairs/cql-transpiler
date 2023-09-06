package gov.va.sparkcql.types;

import java.io.Serializable;

public class Tuple2<T1, T2> implements Serializable {

    public T1 _1;
    public T2 _2;

    public T1 _1() {
        return _1;
    }

    public void _1(T1 _1) {
        this._1 = _1;
    }

    public T2 _2() {
        return _2;
    }

    public void set_1(T2 _2) {
        this._2 = _2;
    }

    public static <T1, T2> Tuple2<T1, T2> of(T1 _1, T2 _2) {
        var t = new Tuple2<T1, T2>();
        t._1 = _1;
        t._2 = _2;
        return t;
    }
}
