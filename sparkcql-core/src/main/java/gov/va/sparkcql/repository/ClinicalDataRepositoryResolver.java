package gov.va.sparkcql.repository;

import java.util.Set;

import javax.xml.namespace.QName;

public class ClinicalDataRepositoryResolver {

    private Set<ClinicalDataRepository<?>> repos;

    public ClinicalDataRepositoryResolver(Set<ClinicalDataRepository<?>> repositories) {
        this.repos = repositories;
    }

    @SuppressWarnings("unchecked")
    public <T> ClinicalDataRepository<T> resolveType(Class<T> entityClass) {
        var repo = repos.stream().filter(r -> r.getEntityClass() == entityClass);
        return (ClinicalDataRepository<T>)repo.findFirst().get();
    }

    @SuppressWarnings("unchecked")
    public <T> ClinicalDataRepository<T> resolveType(QName dataType) {
        var repo = repos.stream().filter(r -> r.getEntityDataType().equals(dataType));
        return (ClinicalDataRepository<T>)repo.findFirst().get();
    }
}