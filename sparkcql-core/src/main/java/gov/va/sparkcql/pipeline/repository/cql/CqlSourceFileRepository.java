package gov.va.sparkcql.pipeline.repository.cql;

import java.util.List;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.pipeline.compiler.CqlParser;
import gov.va.sparkcql.pipeline.repository.AbstractFileRepository;

public class CqlSourceFileRepository extends AbstractFileRepository<CqlSource, VersionedIdentifier> implements CqlSourceRepository {

    public CqlSourceFileRepository(String path) {
        super(path, "cql");
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