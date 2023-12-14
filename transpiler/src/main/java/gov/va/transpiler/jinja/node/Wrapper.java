package gov.va.transpiler.jinja.node;

import gov.va.transpiler.jinja.state.State;

public abstract class Wrapper extends TranspilerNode {

    public Wrapper(State state) {
        super(state);
    }    
}
