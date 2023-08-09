package gov.va.sparkcql.retriever;

import org.apache.spark.sql.Row;

import gov.va.sparkcql.common.repository.QueryableRepository;

public interface ClinicalDataRepository<E> extends QueryableRepository<Row> {

    public Class<E> getEntityClass();
}