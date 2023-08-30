package gov.va.sparkcql.pipeline.repository.cql;

import java.util.List;

import gov.va.sparkcql.types.QualifiedIdentifier;

import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.pipeline.compiler.CqlParser;
import gov.va.sparkcql.pipeline.repository.AbstractFileRepository;

public class CqlSourceFileRepository extends AbstractFileRepository<CqlSource, QualifiedIdentifier> implements CqlSourceRepository {

    public CqlSourceFileRepository(String path) {
        super(path, "cql");
    }

    protected List<CqlSource> cqlSources;

    @Override
    protected CqlSource deserialize(String contents) {
        return new CqlSource()
            .withIdentifier(new QualifiedIdentifier(new CqlParser().parseVersionedIdentifier(contents)))
            .withSource(contents);
    }

    @Override
    protected QualifiedIdentifier getEntityId(CqlSource entity) {
        return entity.getIdentifier();
    }
}