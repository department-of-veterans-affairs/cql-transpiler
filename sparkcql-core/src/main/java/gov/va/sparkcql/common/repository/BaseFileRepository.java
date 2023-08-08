package gov.va.sparkcql.common.repository;

import java.util.List;

import javax.ws.rs.NotSupportedException;

import gov.va.sparkcql.common.io.Directory;

public abstract class BaseFileRepository<K, T> implements Repository<K, T> {

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

    protected abstract K getEntityKey(T entity);

    @Override
    public T findOne(K key) {
        return entities.stream().filter(cs -> key.equals(getEntityKey(cs))).findFirst().orElse(null);
    }

    @Override
    public List<T> findMany(List<K> keys) {
        return entities.stream().filter(cs -> keys.contains(getEntityKey(cs))).toList();
    }

    @Override
    public Boolean exists(K key) {
        var found = entities.stream().filter(cs -> key.equals(getEntityKey(cs))).findFirst();
        return found.isPresent();
    }
}