package gov.va.sparkcql.model;

public class RetrievedEntity {

    // private String retrieveOperationHash;
    // private Class<?> entityClass;
    
    private Object entityData;
    private String contextId;

    public RetrievedEntity() {
    }

    // public String getRetrieveOperationHash() {
    //     return retrieveOperationHash;
    // }

    // public void setRetrieveOperationHash(String retrieveOperationHash) {
    //     this.retrieveOperationHash = retrieveOperationHash;
    // }

    // public Class<?> getEntityClass() {
    //     return entityClass;
    // }

    // public void setEntityClass(Class<?> entityClass) {
    //     this.entityClass = entityClass;
    // }

    public Object getEntityData() {
        return entityData;
    }

    public void setEntityData(Object entityData) {
        this.entityData = entityData;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }
}