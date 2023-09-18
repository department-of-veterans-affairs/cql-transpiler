package gov.va.transpiler.bulk.pyspark;

import java.util.HashSet;
import java.util.LinkedHashSet;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.Element;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.ExpressionRef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Literal;
import org.hl7.elm.r1.UsingDef;

import gov.va.transpiler.ElmConverter;

public class BulkElmToPySparkConverter extends ElmConverter<String, BulkElmToPySparkConverterState> {

    private static final HashSet<Class<? extends Trackable>> implementedOperations = new LinkedHashSet<>();

    {
        implementedOperations.add(Literal.class);
        implementedOperations.add(UsingDef.class);
        implementedOperations.add(ExpressionDef.class);
        implementedOperations.add(ExpressionRef.class);
        implementedOperations.add(Library.class);
    }

    @Override
    protected String defaultResult(Trackable elm, BulkElmToPySparkConverterState context) {
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
    public String visitLiteral(Literal literal, BulkElmToPySparkConverterState context) {
        return literal.getValue();
    }

    @Override
    public String visitExpressionDef(ExpressionDef expressionDef, BulkElmToPySparkConverterState context) {
        return "\n" + expressionDef.getName() + " = " + super.visitExpressionDef(expressionDef, context);
    }

    @Override
    public String visitExpressionRef(ExpressionRef expressionRef, BulkElmToPySparkConverterState context) {
        return expressionRef.getName();
    }

    @Override
    public String visitLibrary(Library library, BulkElmToPySparkConverterState context) {
        String libraryName = "[empty]";
        String libraryVersion = "[empty]";
        if (library.getIdentifier() != null) {
            libraryName = library.getIdentifier().getId();
            libraryVersion = library.getIdentifier().getVersion();
        }
        return "\n# Library" + libraryName + " version " + libraryVersion;
    }

    @Override
    public String convert(Library toConvert, BulkElmToPySparkConverterState state) {
        return visitElement(toConvert, state);
    }
}
