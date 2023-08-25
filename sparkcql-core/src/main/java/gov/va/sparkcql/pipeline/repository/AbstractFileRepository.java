package gov.va.sparkcql.pipeline.repository;

import java.util.List;
import java.util.stream.Collectors;

import gov.va.sparkcql.configuration.SystemConfiguration;
import gov.va.sparkcql.io.Directory;

public abstract class AbstractFileRepository<T, ID> implements ReadableRepository<T, ID> {

    protected List<T> entities;

    public AbstractFileRepository(SystemConfiguration cfg) {
        var path = cfg.getCqlSourceFileRepositoryPath();
        var extension = cfg.getCqlSourceFileRepositoryExt();

        if (path == null || path.isEmpty()) {
            entities = List.of();
        } else {
            this.entities = Directory
                .find(path, extension)
                .map(Directory::readString)
                .map(contents -> deserialize(contents))
                .collect(Collectors.toList());
        }
    }

    protected abstract T deserialize(String contents);

    protected abstract ID getEntityId(T entity);

    @Override
    public T readById(ID id) {
        return entities.stream().filter(cs -> id.equals(getEntityId(cs))).findFirst().orElse(null);
    }

    @Override
    public List<T> readById(List<ID> ids) {
        return entities.stream().filter(cs -> ids.contains(getEntityId(cs))).collect(Collectors.toList());
    }

    @Override
    public Boolean exists(ID id) {
        var found = entities.stream().filter(cs -> id.equals(getEntityId(cs))).findFirst();
        return found.isPresent();
    }
}