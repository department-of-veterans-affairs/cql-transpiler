package gov.va.transpiler.bulk.pyspark;

import java.util.HashSet;
import java.util.LinkedHashSet;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.ExpressionRef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Literal;
import org.hl7.elm.r1.Tuple;
import org.hl7.elm.r1.TupleElement;
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
    public String visitTupleElement(TupleElement tupleElement, BulkElmToPySparkConverterState context) {
        return "\n" + tupleElement.getName() + " = " + super.visitTupleElement(tupleElement, context);
    }

    @Override
    public String visitLibrary(Library library, BulkElmToPySparkConverterState context) {
        // TODO: when visiting a library definition, we should create a new python file using a specific naming convention so it can be referenced like libraries are in CQL
        String libraryName = "[empty]";
        String libraryVersion = "[empty]";
        if (library.getIdentifier() != null) {
            libraryName = library.getIdentifier().getId();
            libraryVersion = library.getIdentifier().getVersion();
        }
        return "# Library " + libraryName + " version " + libraryVersion + super.visitLibrary(library, context);
    }

    @Override
    public String convert(Library toConvert, BulkElmToPySparkConverterState state) {
        return visitElement(toConvert, state);
    }
}
