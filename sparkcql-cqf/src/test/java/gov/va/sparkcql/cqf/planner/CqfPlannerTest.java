package gov.va.sparkcql.cqf.planner;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.sparkcql.cqf.domain.RetrieveDefinition;
import org.hl7.elm.r1.VersionedIdentifier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CqfPlannerTest {

    @Test
    public void should_extract_retrieve_list() {
        var compiler = new CqfCompiler(new FileLibrarySourceProvider("./src/test/resources/cql"));
        var output = compiler.compile(List.of(new VersionedIdentifier().withId("Retrievals").withVersion("1.0")));
        var planner = new CqfPlanner();
        var retrieves = planner.extractRetrieveDefinitions(output);
        assertEquals(3, retrieves.size());

        var e1 = retrieves.get(0);
        assertEquals("Encounter", e1.getDataType().getLocalPart());

        var e2 = retrieves.get(1);
        assertEquals("encounter-qicore-qicore-encounter", e2.getTemplateId());
        assertEquals(1, e2.getFilters().size());
        assertEquals("Ambulatory/ED Visit", e2.getFilters().get(0).getValue());
        assertEquals(RetrieveDefinition.Comparator.IN, e2.getFilters().get(0).getComparator());

        var p = retrieves.get(2);
        assertEquals("Patient", p.getDataType().getLocalPart());
        assertEquals("patient-qicore-qicore-patient", p.getTemplateId());
    }
}