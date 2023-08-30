package gov.va.sparkcql.pipeline.repository.cql;

import gov.va.sparkcql.types.QualifiedIdentifier;

import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.pipeline.repository.ReadableRepository;

public interface CqlSourceRepository extends ReadableRepository<CqlSource, QualifiedIdentifier> {
}