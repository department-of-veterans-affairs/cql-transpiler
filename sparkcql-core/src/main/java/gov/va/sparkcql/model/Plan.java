package gov.va.sparkcql.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonMapper;

public class Plan implements Serializable {
    
    private List<RetrievalOperation> retrievalOperations;

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

    public void setContextSpecifier(String contextSpecifier) {
    }

    public String getContextSpecifier() {
        return "Patient";       // TODO
    }

	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException
	{
        var plan = ElmJsonMapper.getMapper().readValue(input.readUTF(), Plan.class);
        this.retrievalOperations = plan.retrievalOperations;
	}

	private void writeObject(ObjectOutputStream output) throws IOException
	{
        var json = ElmJsonMapper.getMapper().writeValueAsString(this);
        output.writeObject(json);
	}
}