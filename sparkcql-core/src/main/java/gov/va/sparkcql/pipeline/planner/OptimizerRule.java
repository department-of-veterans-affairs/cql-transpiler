package gov.va.sparkcql.pipeline.planner;

import org.hl7.elm.r1.Element;

import gov.va.sparkcql.domain.RetrievalOperation;

public interface OptimizerRule {
    
    public Boolean test(Element elm);

    public void apply(RetrievalOperation op, Element elm);
}