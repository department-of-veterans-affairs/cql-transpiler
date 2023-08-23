package gov.va.sparkcql.pipeline.repository.terminology;

import org.apache.spark.sql.Row;

import gov.va.sparkcql.pipeline.repository.QueryableRepository;
import gov.va.sparkcql.types.DataType;

public interface TerminologyRepository<E> extends QueryableRepository<Row> {

    public Class<E> getEntityClass();

    public DataType getEntityDataType();
}