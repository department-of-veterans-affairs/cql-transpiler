package gov.va.sparkcql.repository.clinical;

import java.util.Set;

import com.google.inject.Inject;

import gov.va.sparkcql.types.DataType;

public class ClinicalRepositoryResolver {

    private Set<ClinicalRepository<?>> repos;

    @Inject
    public ClinicalRepositoryResolver(Set<ClinicalRepository<?>> repositories) {
        this.repos = repositories;
    }

    @SuppressWarnings("unchecked")
    public <T> ClinicalRepository<T> resolveType(Class<T> entityClass) {
        var repo = repos.stream().filter(r -> r.getEntityClass() == entityClass);
        return (ClinicalRepository<T>)repo.findFirst().get();
    }

    @SuppressWarnings("unchecked")
    public <T> ClinicalRepository<T> resolveType(DataType dataType) {
        var repo = repos.stream().filter(r -> r.getEntityDataType().equals(dataType));
        return (ClinicalRepository<T>)repo.findFirst().get();
    }
}