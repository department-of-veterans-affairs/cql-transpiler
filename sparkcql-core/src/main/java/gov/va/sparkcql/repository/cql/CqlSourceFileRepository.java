package gov.va.sparkcql.repository.cql;

import java.util.List;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.repository.AbstractFileRepository;
import gov.va.sparkcql.repository.FileRepositoryConfiguration;
import gov.va.sparkcql.service.compiler.CqlParser;

public class CqlSourceFileRepository extends AbstractFileRepository<CqlSource, VersionedIdentifier> implements CqlSourceRepository {

    public CqlSourceFileRepository(FileRepositoryConfiguration cfg) {
        super(cfg);
    }

    protected List<CqlSource> cqlSources;

    @Override
    protected CqlSource deserialize(String contents) {
        return new CqlSource()
            .withIdentifier(new CqlParser().parseVersionedIdentifier(contents))
            .withSource(contents);
    }

    @Override
    protected VersionedIdentifier getEntityId(CqlSource entity) {
        return entity.getIdentifier();
    }   
}