package gov.va.sparkcql.model;

public class ContextualizedEntity<T> {
   
    private Object entity;
    private String contextId;

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }
}