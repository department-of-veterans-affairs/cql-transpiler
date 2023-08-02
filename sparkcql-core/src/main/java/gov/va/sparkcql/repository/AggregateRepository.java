package gov.va.sparkcql.repository;

import java.util.List;

public class AggregateRepository<K, T> implements Repository<K, T> {

    List<Repository<K, T>> repositories;

    public AggregateRepository(List<Repository<K, T>> repositories) {
        this.repositories = repositories;
    }

    @Override
    public T findOne(K key) {
        var eligibleSources = repositories.stream().filter(a -> a.exists(key)).toList();
        if (eligibleSources.size() > 0) {
            var found = eligibleSources.stream().map(repository -> {
                var r = repository.findOne(key);
                if (r == null) {
                    throw new RuntimeException(repository.getClass().getSimpleName() + " stated support for type '" + key.toString() + "' but none was found.");
                } else {
                    return r;
                }
            });
            return found;
        } else {
            throw new RuntimeException("Attempted to query missing data for type '" + dataType.toString() + "' without verifying data was present.");
        }
    }

    @Override
    public List<T> findMany(Object filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findMany'");
    }

    @Override
    public List<T> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Boolean exists(K key) {
        return this.repositories.stream().filter(a -> a.exists(key)).count() > 0;
    }
}
