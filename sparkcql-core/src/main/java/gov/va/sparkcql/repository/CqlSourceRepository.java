package gov.va.sparkcql.repository;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.entity.CqlSource;

public interface CqlSourceRepository extends PointRepository<CqlSource, VersionedIdentifier> {
}