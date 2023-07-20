package gov.va.sparkcql.common;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class FunctionalExpressions {

    public static <T> T iif(boolean test, Supplier<T> ifTrue, Supplier<T> ifFalse) {
        return test ? ifTrue.get() : ifFalse.get();
    }

    // public static <R, T1> R match(Object matched, Class<T1> class1, Function<T1, R> case1) {
    //     if (matched.getClass() == class1) {
    //         return case1.apply((T1)matched);
    //     } else {
    //         return null;
    //     }
    // }

    // public static <R, T1, T2> R match(Object matched, Class<T1> class1, Class<T2> class2, Function<T1, R> case1, Function<T2, R> case2) {
    //     if (matched == null) {
    //         return null;
    //     } else if (matched.getClass() == class1) {
    //         return case1.apply((T1)matched);
    //     } if (matched.getClass() == class2) {
    //         return case2.apply((T2)matched);
    //     }
    //     return null;
    // }

    // public static <R> R match(Object matched, Function<Object, Object>... cases) {
    //     for (var f : cases) {
    //         var r = f.apply(matched);
    //         if (r != null) {
    //             return (R)r;
    //         }
    //     }
    //     return null;
    // }

    public static Match match(Object matchExpression) {
        return new Match(matchExpression);
    }

    static class Case {
        protected Function<?, Object> invoke;
        protected Class<?> typePredicate;
    }

    static public class Match {

        private Object matchExpression;
        private ArrayList<Case> cases = new ArrayList<Case>();

        public Match(Object matchExpression) {
            this.matchExpression = matchExpression;
        }

        public <T, R> Match on(Class<T> predicate, Function<T, Object> invoke) {
            var c = new FunctionalExpressions.Case();
            c.typePredicate = predicate;
            c.invoke = invoke;
            this.cases.add(c);
            return this;
        }

        public <R> R end(Supplier<Object> elseInvoke) {
            for (var c : this.cases) {
                if (c.typePredicate.isAssignableFrom(matchExpression.getClass())) {
                    Function<Object, Object> f = (Function<Object, Object>)c.invoke;
                    var r = f.apply(this.matchExpression);
                    if (r != null) {
                        return (R)r;
                    }
                }
            }
            return (R)elseInvoke.get();
        }

        public <R> R end() {
            return end(() -> null);
        }
    }

    public static <T, R> Function<Object, Object> on(Class<T> predicate, Function<T, R> invoke) {
        return (Object t) -> {
            if (t != null && t.getClass() == predicate) {
                return invoke.apply((T)t);
            } else {
                return null;
            }
        };
    }

    public static <T, R> Function<T, R> on(Function<T, R> invoke) {
        return (T t) -> {
            return invoke.apply(t);
        };
    }
}