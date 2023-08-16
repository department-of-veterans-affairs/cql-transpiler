package gov.va.sparkcql.repository.clinical;

import org.apache.spark.sql.Row;

import gov.va.sparkcql.repository.QueryableRepository;
import gov.va.sparkcql.types.DataType;

public interface ClinicalRepository<E> extends QueryableRepository<Row> {

    public Class<E> getEntityClass();

    public DataType getEntityDataType();
}