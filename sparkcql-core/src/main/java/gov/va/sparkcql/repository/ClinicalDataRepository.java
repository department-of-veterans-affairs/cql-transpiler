package gov.va.sparkcql.repository;

import org.apache.spark.sql.Row;

import gov.va.sparkcql.entity.DataType;

public interface ClinicalDataRepository<E> extends QueryableRepository<Row> {

    public Class<E> getEntityClass();

    public DataType getEntityDataType();
}