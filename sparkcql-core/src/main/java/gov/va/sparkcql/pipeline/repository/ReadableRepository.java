package gov.va.sparkcql.pipeline.repository;

import java.util.List;

public interface ReadableRepository<T, ID> extends Repository {

    T readById(ID id);

    List<T> readById(List<ID> ids);

    Boolean exists(ID id);
}