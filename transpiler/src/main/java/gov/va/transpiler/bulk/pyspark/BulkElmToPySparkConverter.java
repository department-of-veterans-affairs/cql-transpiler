package gov.va.transpiler.bulk.pyspark;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Literal;

import gov.va.transpiler.ElmConverter;
import gov.va.transpiler.bulk.pyspark.output.LiteralNode;
import gov.va.transpiler.output.DefaultOutputNode;
import gov.va.transpiler.output.OutputNode;

public class BulkElmToPySparkConverter extends ElmConverter<OutputNode, BulkElmToPySparkConverterState> {

    @Override
    public OutputNode convert(Library library, BulkElmToPySparkConverterState state) {
        return visitElement(library, state);
    }

    @Override
    public OutputNode visitLiteral(Literal literal, BulkElmToPySparkConverterState context) {
        return new LiteralNode(literal.getValue());
    }

    @Override
    protected OutputNode defaultResult(Trackable elm, BulkElmToPySparkConverterState context) {
        return new DefaultOutputNode(defaultNodeName(elm));
    }

    @Override
    protected OutputNode aggregateResult(OutputNode aggregate, OutputNode nextResult) {
        if (nextResult != null) {
            aggregate.addChild(nextResult);
        }
        return aggregate;
    }

    private String defaultNodeName(Trackable elm) {
        return elm == null ? "NullElement" : elm.getClass().getSimpleName();
    }
}
