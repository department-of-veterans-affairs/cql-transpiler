package gov.va.sparkcql.planner;

import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.model.Plan;

public interface Planner {
    
    public Plan plan(List<Library> libraries);
}