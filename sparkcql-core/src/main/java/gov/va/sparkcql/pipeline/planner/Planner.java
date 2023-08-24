package gov.va.sparkcql.pipeline.planner;

import java.util.List;

import gov.va.sparkcql.pipeline.Stage;
import org.hl7.elm.r1.Library;

import gov.va.sparkcql.domain.Plan;

public interface Planner extends Stage {
        
    public Plan plan(List<Library> libraries);
}