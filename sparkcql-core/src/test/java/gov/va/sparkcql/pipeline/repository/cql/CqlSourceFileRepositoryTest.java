package gov.va.sparkcql.pipeline.repository.cql;

import gov.va.sparkcql.types.QualifiedIdentifier;
import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepository;

import static org.junit.jupiter.api.Assertions.*;

public class CqlSourceFileRepositoryTest {

    @Test
    public void should_support_basic_read_ops() {
        var repository = new CqlSourceFileRepository("./src/test/resources/mock-model/cql");
        assertNotNull(repository.readById(new QualifiedIdentifier().withId("MOCK_LIBRARY").withVersion("1.0")));
        assertThrows(RuntimeException.class, () -> repository.readById(new QualifiedIdentifier().withId("MOCK_LIBRARY")));
    }
}