package gov.va.sparkcql.compiler;

import java.util.List;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.model.CqlSource;
import gov.va.sparkcql.common.configuration.Configuration;
import gov.va.sparkcql.common.repository.BaseFileRepository;
import gov.va.sparkcql.common.configuration.ConfigKey;

public class CqlSourceFileRepository extends BaseFileRepository<CqlSource, VersionedIdentifier> implements CqlSourceRepository {

    protected List<CqlSource> cqlSources;

    public CqlSourceFileRepository() {
        this(Configuration.getGlobalConfig());
    }

    public CqlSourceFileRepository(Configuration cfg) {
        super(cfg.read(ConfigKey.SPARKCQL_CQLSOURCEREPOSITORY_PATH), "cql");
    }

    public CqlSourceFileRepository(String path) {
        super(path, "cql");
    }

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