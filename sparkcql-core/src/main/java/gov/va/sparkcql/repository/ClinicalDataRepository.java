package gov.va.sparkcql.repository;

import org.apache.spark.sql.Row;

public interface ClinicalDataRepository<E> extends QueryableRepository<Row> {

    public Class<E> getEntityClass();
}