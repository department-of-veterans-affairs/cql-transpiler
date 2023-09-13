package gov.va.sparkcql.translator.pyspark;

import gov.va.sparkcql.translator.ElmToScriptEngine;
import gov.va.sparkcql.translator.ScriptEngineState;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.Element;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.UsingDef;

public class PySparkElmToScriptEngine extends ElmToScriptEngine {
    @Override
    public String visitElement(Element element, ScriptEngineState context) {
        String result = super.visitElement(element, context);
        // TODO: to NOT modify this function... delete it once the relevant functions are implemented
        return result;
    }

    @Override
    public String defaultResult(Trackable element, ScriptEngineState context) {
        return element == null ? null : element.toString();
    }

    @Override
    public String visitUsingDef(UsingDef element, ScriptEngineState context) {
        return null;
    }

    @Override
    public String visitExpressionDef(ExpressionDef element, ScriptEngineState context) {
        // TODO
        return super.visitExpressionDef(element, context);
    }
}
