package gov.va.sparkcql.pipeline.repository.cql;

import java.util.List;

import org.hl7.elm.r1.VersionedIdentifier;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SystemConfiguration;
import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.pipeline.compiler.CqlParser;
import gov.va.sparkcql.pipeline.repository.AbstractFileRepository;

public class CqlSourceFileRepository extends AbstractFileRepository<CqlSource, VersionedIdentifier> implements CqlSourceRepository {

    @Inject
    public CqlSourceFileRepository(SystemConfiguration cfg) {
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