package gov.va.sparkcql.translator.pyspark;

import gov.va.sparkcql.translator.ScriptEngineState;

// TODO
public class PysparkElmToScriptEngineState extends ScriptEngineState {

    public String internalString = "";

    @Override
    public String toScript() {
        return internalString;
    }
}
