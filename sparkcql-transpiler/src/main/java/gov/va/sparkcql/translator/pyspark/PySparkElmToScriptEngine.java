package gov.va.sparkcql.translator.pyspark;

import gov.va.sparkcql.translator.ElmToScriptEngine;
import gov.va.sparkcql.translator.ScriptEngineState;

import java.util.HashSet;
import java.util.LinkedHashSet;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.ExpressionRef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Literal;
import org.hl7.elm.r1.UsingDef;

public class PySparkElmToScriptEngine extends ElmToScriptEngine {

    private static final HashSet<Class<? extends Trackable>> implementedOperations = new LinkedHashSet<>();

    {
        implementedOperations.add(Literal.class);
        implementedOperations.add(UsingDef.class);
        implementedOperations.add(ExpressionDef.class);
        implementedOperations.add(ExpressionRef.class);
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
    public String visitLibrary(Library library, ScriptEngineState context) {
        // TODO: the 'Library' function in CQL actually defines the name and version of the library comprised of the current code, so it can be externally referenced
        // Will probably want to transform libraries into a node that matches the Python semantics of referencing external code
        /*
        String importString = "";
        if (library.getIdentifier() != null) {
            importString = "\nimport " + library.getIdentifier().getId();
            if (library.getIdentifier().getVersion() != null) {
                // TODO need to decide how we handle import versions of libraries since CQL/Python do things pretty differently
                importString += "_" + library.getIdentifier().getVersion();
            }
        }
        return importString + super.visitLibrary(library, context);
        */
        return super.visitLibrary(library, context);
    }

    @Override
    public String visitLiteral(Literal literal, ScriptEngineState context) {
        return literal.getValue();
    }

    @Override
    public String visitExpressionDef(ExpressionDef expressionDef, ScriptEngineState context) {
        return "\n" + expressionDef.getName() + " = " + super.visitExpressionDef(expressionDef, context);
    }

    @Override
    public String visitExpressionRef(ExpressionRef expressionRef, ScriptEngineState context) {
        return expressionRef.getName();
    }
}
