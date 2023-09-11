package gov.va.sparkcql.pipeline.repository.cql;

import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.types.QualifiedIdentifier;

import java.util.List;

public class CqlSourceNullRepository implements CqlSourceRepository {

    @Override
    public CqlSource readById(QualifiedIdentifier qualifiedIdentifier) {
        return null;
    }

    @Override
    public List<CqlSource> readById(List<QualifiedIdentifier> qualifiedIdentifiers) {
        return List.of();
    }

    @Override
    public Boolean exists(QualifiedIdentifier qualifiedIdentifier) {
        return false;
    }
}
