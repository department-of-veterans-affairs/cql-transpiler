package gov.va.sparkcql.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonMapper;
import org.hl7.elm.r1.ContextDef;

public class Plan implements Serializable {
    
    private List<Retrieval> retrieves;

    private ContextDef contextDef;

    public List<Retrieval> getRetrieves() {
        return retrieves;
    }

    public void setRetrieves(List<Retrieval> retrieves) {
        this.retrieves = retrieves;
    }

    public Plan withRetrieves(List<Retrieval> retrieves) {
        this.retrieves = retrieves;
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
        this.retrieves = plan.retrieves;
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
        var json = ElmJsonMapper.getMapper().writeValueAsString(this);
        output.writeObject(json);
	}
}