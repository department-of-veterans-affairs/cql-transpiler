package gov.va.sparkcql.pipeline.planner;

import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.Component;

public interface Planner extends Component {
    
    public Plan plan(List<Library> libraries);
}