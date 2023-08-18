package gov.va.sparkcql.domain;

import java.util.Base64;
import java.util.Objects;

import org.hl7.elm.r1.Retrieve;

public class RetrievalOperation {
    
    private Retrieve retrieve;

    public String getHashKey() {
        var uniqueParts = retrieve.getDataType().toString();        // concat additional variables here
        var encoder = Base64.getEncoder();
        var encoded = encoder.encodeToString(uniqueParts.getBytes());
        return encoded;
    }

    public Retrieve getRetrieve() {
        return retrieve;
    }

    public void setRetrieve(Retrieve retrieve) {
        this.retrieve = retrieve;
    }

    public RetrievalOperation withRetrieve(Retrieve retrieve) {
        this.retrieve = retrieve;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RetrievalOperation) {
            return this.getHashKey().equals(((RetrievalOperation)o).getHashKey());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getHashKey());
    }    
}