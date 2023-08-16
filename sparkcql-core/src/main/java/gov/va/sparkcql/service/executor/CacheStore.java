package gov.va.sparkcql.service.executor;

import java.time.LocalDateTime;

import javax.xml.namespace.QName;

import gov.va.sparkcql.domain.RetrievalOperation;

public interface CacheStore {

    public Boolean isCached();
    
    public String makeCacheToken(RetrievalOperation retrieveOperation);

    public LocalDateTime sourceRefreshDateTime(QName dataType);
}