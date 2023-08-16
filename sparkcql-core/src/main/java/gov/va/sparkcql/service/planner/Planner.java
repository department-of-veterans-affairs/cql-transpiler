package gov.va.sparkcql.service.planner;

import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.domain.Plan;

public interface Planner {
    
    public Plan plan(List<Library> libraries);
}