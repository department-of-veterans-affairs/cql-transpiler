package gov.va.sparkcql.repository;

import java.util.Set;

import com.google.inject.Inject;

import gov.va.sparkcql.entity.DataType;

public class ClinicalDataRepositoryResolver {

    private Set<ClinicalDataRepository<?>> repos;

    @Inject
    public ClinicalDataRepositoryResolver(Set<ClinicalDataRepository<?>> repositories) {
        this.repos = repositories;
    }

    @SuppressWarnings("unchecked")
    public <T> ClinicalDataRepository<T> resolveType(Class<T> entityClass) {
        var repo = repos.stream().filter(r -> r.getEntityClass() == entityClass);
        return (ClinicalDataRepository<T>)repo.findFirst().get();
    }

    @SuppressWarnings("unchecked")
    public <T> ClinicalDataRepository<T> resolveType(DataType dataType) {
        var repo = repos.stream().filter(r -> r.getEntityDataType().equals(dataType));
        return (ClinicalDataRepository<T>)repo.findFirst().get();
    }
}