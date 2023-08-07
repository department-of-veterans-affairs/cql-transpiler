package gov.va.sparkcql.repository;

import java.util.List;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.model.CqlSource;
import gov.va.sparkcql.common.configuration.Configuration;
import gov.va.sparkcql.common.configuration.ConfigKey;
import gov.va.sparkcql.compiler.CompilerGateway;

public class CqlSourceFileRepository extends BaseFileRepository<VersionedIdentifier, CqlSource> implements CqlSourceRepository {

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
        var compilerGateway = new CompilerGateway();
        return new CqlSource()
            .withIdentifier(compilerGateway.parseVersionedIdentifier(contents))
            .withSource(contents);
    }

    @Override
    protected VersionedIdentifier getEntityKey(CqlSource entity) {
        return entity.getIdentifier();
    }   
}