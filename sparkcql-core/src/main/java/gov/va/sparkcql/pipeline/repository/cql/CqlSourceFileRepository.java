package gov.va.sparkcql.pipeline.repository.cql;

import java.util.List;

import org.hl7.elm.r1.VersionedIdentifier;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.pipeline.compiler.CqlParser;
import gov.va.sparkcql.pipeline.repository.AbstractFileRepository;

public class CqlSourceFileRepository extends AbstractFileRepository<CqlSource, VersionedIdentifier> implements CqlSourceRepository {

    public static final String CQL_SOURCE_FILE_REPOSITORY_PATH = "sparkcql.cqlsourcefilerepository.path";
    public static final String CQL_SOURCE_FILE_REPOSITORY_EXT = "sparkcql.cqlsourcefilerepository.extension";

    @Inject
    public CqlSourceFileRepository(EnvironmentConfiguration cfg) {
        super(
                cfg.readSetting(CQL_SOURCE_FILE_REPOSITORY_PATH, "./"),
                cfg.readSetting(CQL_SOURCE_FILE_REPOSITORY_EXT, "cql"));
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