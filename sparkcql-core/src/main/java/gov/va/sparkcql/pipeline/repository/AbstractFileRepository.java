package gov.va.sparkcql.pipeline.repository;

import java.util.List;
import java.util.stream.Collectors;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.io.Directory;
import gov.va.sparkcql.io.Files;

public abstract class AbstractFileRepository<T, ID> implements ReadableRepository<T, ID> {

    protected List<T> entities;

    public AbstractFileRepository(String path, String extension) {
        if (path == null || path.isEmpty()) {
            entities = List.of();
        } else {
            this.entities = Directory
                .find(path, extension)
                .map(Files::readFile)
                .map(this::deserialize)
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