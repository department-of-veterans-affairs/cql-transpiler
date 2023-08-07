package gov.va.sparkcql.repository;

public interface DataRepository {

    <T> T findOne(Class<T> clazz);

    default void test() {
        var x = this.findOne(String.class);
    }
}