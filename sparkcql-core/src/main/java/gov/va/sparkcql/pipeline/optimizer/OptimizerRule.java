package gov.va.sparkcql.pipeline.optimizer;

import org.hl7.elm.r1.Element;
import org.hl7.elm.r1.Retrieve;

public interface OptimizerRule {
    
    public Boolean test(Element elm);

    public void apply(Retrieve op, Element elm);
}