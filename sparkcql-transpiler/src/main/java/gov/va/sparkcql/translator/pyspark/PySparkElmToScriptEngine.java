package gov.va.sparkcql.translator.pyspark;

import gov.va.sparkcql.translator.ElmToScriptEngine;
import gov.va.sparkcql.translator.ScriptEngineState;

import java.util.HashSet;
import java.util.LinkedHashSet;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Literal;
import org.hl7.elm.r1.UsingDef;

public class PySparkElmToScriptEngine extends ElmToScriptEngine {

    private static final HashSet<Class<? extends Trackable>> implementedOperations = new LinkedHashSet<>();

    {
        implementedOperations.add(Literal.class);
        implementedOperations.add(UsingDef.class);
        implementedOperations.add(ExpressionDef.class);
        implementedOperations.add(Library.class);
    }

    @Override
    protected String defaultResult(Trackable elm, ScriptEngineState context) {
        return elm == null || implementedOperations.contains(elm.getClass()) ? "" : elm.getClass().getSimpleName();
    }

    @Override
    protected String aggregateResult(String aggregate, String nextResult) {
        if (aggregate == null && nextResult == null) {
            return null;
        }
        if (aggregate == null || nextResult == null) {
            return aggregate == null ? nextResult : aggregate;
        }
        return aggregate + nextResult;
    }

    @Override
    public String visitLiteral(Literal literal, ScriptEngineState context) {
        return literal.getValue();
    }

    @Override
    public String visitExpressionDef(ExpressionDef expressionDef, ScriptEngineState context) {
        return expressionDef.getName() + " = " + super.visitExpressionDef(expressionDef, context);
    }
}
