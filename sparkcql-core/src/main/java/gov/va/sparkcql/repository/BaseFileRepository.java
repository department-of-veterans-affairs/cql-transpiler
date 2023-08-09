package gov.va.sparkcql.repository;

import java.util.List;

import javax.ws.rs.NotSupportedException;

import gov.va.sparkcql.common.io.Directory;

public abstract class BaseFileRepository<T, ID> implements PointRepository<T, ID> {

    protected List<T> entities;

    public BaseFileRepository() {
        throw new NotSupportedException("Must override default constructor.");
    }

    public BaseFileRepository(String path, String extension) {
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