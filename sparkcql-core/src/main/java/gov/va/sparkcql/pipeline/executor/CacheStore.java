package gov.va.sparkcql.pipeline.executor;

import org.hl7.elm.r1.Retrieve;

import java.time.LocalDateTime;

import javax.xml.namespace.QName;


public interface CacheStore {

    public Boolean isCached();
    
    public String makeCacheToken(Retrieve retrieveOperation);

    public LocalDateTime sourceRefreshDateTime(QName dataType);
}