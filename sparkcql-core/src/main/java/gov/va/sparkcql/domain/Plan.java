package gov.va.sparkcql.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonMapper;
import org.hl7.elm.r1.ContextDef;

public class Plan implements Serializable {
    
    private List<RetrievalOperation> retrievalOperations;

    private ContextDef contextDef;

    public List<RetrievalOperation> getRetrievalOperations() {
        return retrievalOperations;
    }

    public void setRetrievalOperations(List<RetrievalOperation> retrievalOperations) {
        this.retrievalOperations = retrievalOperations;
    }

    public Plan withRetrievalOperations(List<RetrievalOperation> retrievalOperations) {
        this.retrievalOperations = retrievalOperations;
        return this;
    }

    public ContextDef getContextDef() {
        return this.contextDef;
    }

    public void setContextDef(ContextDef contextDef) {
        this.contextDef = contextDef;
    }

    // The ELM encounters serialization issues during Spark broadcasting so we implement serialization manually.
	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
        String json = (String)input.readObject();
        var plan = ElmJsonMapper.getMapper().readValue(json, Plan.class);
        this.retrievalOperations = plan.retrievalOperations;
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
        var json = ElmJsonMapper.getMapper().writeValueAsString(this);
        output.writeObject(json);
	}
}