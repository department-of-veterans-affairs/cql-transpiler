package gov.va.sparkcql.pipeline.repository;

import java.util.List;
import java.util.stream.Collectors;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.io.Directory;
import gov.va.sparkcql.io.Files;

public abstract class AbstractFileRepository<T, ID> implements ReadableRepository<T, ID> {

    protected List<T> entities;

    private String path;

    public AbstractFileRepository(String path, String extension) {
        this.path = path;
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
        validate();
        return entities.stream()
                .filter(cs -> id.equals(getEntityId(cs)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to find " + id.toString() + " in repository."));
    }

    @Override
    public List<T> readById(List<ID> ids) {
        validate();
        return ids.stream()
                .map(this::readById)
                .collect(Collectors.toList());
    }

    private void validate() {
        if (this.entities.isEmpty()) {
            throw new RuntimeException("Attempted to read from an empty repository. Check location '" + path + "'.");
        }
    }

    @Override
    public Boolean exists(ID id) {
        var found = entities.stream().filter(cs -> id.equals(getEntityId(cs))).findFirst();
        return found.isPresent();
    }
}