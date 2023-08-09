package gov.va.sparkcql.common.repository;

import java.util.List;

public interface PointRepository<T, ID> {

    T findOne(ID id);

    List<T> findMany(List<ID> ids);

    Boolean exists(ID id);
}