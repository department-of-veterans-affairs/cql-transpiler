package gov.va.sparkcql.repository;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.compiler.IdentifiedCqlSource;

public interface CqlSourceRepository extends Repository<VersionedIdentifier, IdentifiedCqlSource> {
}