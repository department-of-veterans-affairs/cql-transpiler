package gov.va.sparkcql.repository;

import gov.va.sparkcql.common.repository.Repository;
import gov.va.sparkcql.model.ClinicalTable;

public interface ClinicalDataRepository<K, T> extends Repository<K, ClinicalTable<T>> {
}