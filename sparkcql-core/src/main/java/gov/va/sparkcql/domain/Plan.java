package gov.va.sparkcql.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonMapper;
import org.hl7.elm.r1.ContextDef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

public class Plan implements Serializable {

    private List<Library> libraries;

    private List<Retrieval> retrieves;

    public Plan() {
    }

    public Plan(Plan copy) {
        this.libraries = copy.libraries;
        this.retrieves = copy.retrieves;
    }

    public Optional<Library> getLibrary(int index) {
        return Optional.of(libraries.get(0));
    }

    public Optional<Library> getLibrary(VersionedIdentifier versionedIdentifier) {
        return libraries.stream()
                .filter(l -> l.getIdentifier().equals(versionedIdentifier))
                .findFirst();
    }

    public List<Library> getLibraries() {
        return this.libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

    public Plan withLibraries(List<Library> libraries) {
        this.libraries = libraries;
        return this;
    }

    public Plan withLibrary(Library library) {
        this.libraries = List.of(library);
        return this;
    }

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

    public ContextDef resolveContextDef() {
        if (this.libraries == null || this.libraries.isEmpty())
            throw new RuntimeException("Unable to determine plan context. No compiled libraries found.");
        if (this.libraries.get(0).getContexts().getDef().size() > 1)
            throw new RuntimeException("Unable to determine plan context. Multiple context definitions not supported.");

        return this.libraries.get(0).getContexts().getDef().get(0);
    }

    public boolean isOptimized() {
        return this.retrieves != null;
    }

    // The ELM encounters serialization issues during Spark broadcasting so we implement serialization manually.
	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
        String json = (String)input.readObject();
        var plan = ElmJsonMapper.getMapper().readValue(json, Plan.class);
        this.libraries = plan.libraries;
        this.retrieves = plan.retrieves;
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
        var json = ElmJsonMapper.getMapper().writeValueAsString(this);
        output.writeObject(json);
	}
}