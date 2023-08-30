package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.Plan;
import org.cqframework.cql.cql2elm.model.CompiledLibrary;
import org.hl7.elm.r1.VersionedIdentifier;

import java.util.Map;
import java.util.stream.Collectors;

public class LibraryCacheAdapter {

    private final Plan plan;

    public LibraryCacheAdapter(Plan plan) {
        this.plan = plan;
    }

    public Map<VersionedIdentifier, CompiledLibrary> getVersionedIdentifierToCompiledLibraryMap() {
        return plan.getLibraries().stream().map(l -> {
            var compiledLibrary = new CompiledLibrary();
            compiledLibrary.setIdentifier(l.getIdentifier());
            compiledLibrary.setLibrary(l);
            return compiledLibrary;
        }).collect(Collectors.toMap(CompiledLibrary::getIdentifier, v -> v));
    }

    public Plan getPlan() {
        return plan;
    }
}
