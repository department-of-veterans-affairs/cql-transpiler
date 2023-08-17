package gov.va.sparkcql.repository;

import java.util.List;

import gov.va.sparkcql.configuration.SystemConfiguration;
import gov.va.sparkcql.io.Directory;

public abstract class AbstractFileRepository<T, ID> implements PointRepository<T, ID> {

    protected List<T> entities;

    public AbstractFileRepository(SystemConfiguration cfg) {
        var path = cfg.getCqlSourceFileRepositoryPath();
        var extension = cfg.getCqlSourceFileRepositoryExt();

        if (path == null || path == "") {
            entities = List.of();
        } else {
            this.entities = Directory
                .find(path, extension)
                .map(Directory::readString)
                .map(contents -> deserialize(contents))
                .toList();
        }
    }

    protected abstract T deserialize(String contents);

    protected abstract ID getEntityId(T entity);

    @Override
    public T findOne(ID id) {
        return entities.stream().filter(cs -> id.equals(getEntityId(cs))).findFirst().orElse(null);
    }

    @Override
    public List<T> findMany(List<ID> ids) {
        return entities.stream().filter(cs -> ids.contains(getEntityId(cs))).toList();
    }

    @Override
    public Boolean exists(ID id) {
        var found = entities.stream().filter(cs -> id.equals(getEntityId(cs))).findFirst();
        return found.isPresent();
    }
}