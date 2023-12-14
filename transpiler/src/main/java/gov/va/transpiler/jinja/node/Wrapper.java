package gov.va.transpiler.jinja.node;

import gov.va.transpiler.jinja.converter.State;

public abstract class Wrapper extends TranspilerNode {

    public Wrapper(State state) {
        super(state);
    }    
}
