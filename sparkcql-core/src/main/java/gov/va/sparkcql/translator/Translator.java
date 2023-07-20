package gov.va.sparkcql.translator;

import java.util.List;
import java.util.Map;

import org.apache.spark.sql.Encoders;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.*;

import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.elm.r1.*;

import gov.va.sparkcql.adapter.data.DataAdapter;
import gov.va.sparkcql.adapter.data.DataAdapterAggregator;
import gov.va.sparkcql.adapter.library.LibraryAdapter;
import gov.va.sparkcql.adapter.library.LibraryAdapterAggregator;
import gov.va.sparkcql.adapter.model.ModelAdapter;
import gov.va.sparkcql.adapter.model.ModelAdapterAggregator;
import gov.va.sparkcql.common.Log;
import gov.va.sparkcql.translator.result.Result;
import gov.va.sparkcql.translator.result.DataResult;
import gov.va.sparkcql.translator.result.ValueResult;

import static gov.va.sparkcql.common.FunctionalExpressions.match;

@SuppressWarnings("unchecked")
public class Translator extends ElmBaseLibraryVisitor <Result, Environment> {

    private SparkSession spark;
    private ModelAdapterAggregator modelAggregate;
    private LibraryAdapterAggregator libraryAggregate;
    private DataAdapterAggregator dataAggregate;
    private Map<String, Object> parameters;

    public Translator(SparkSession spark, List<ModelAdapter> modelAdapters, List<LibraryAdapter> libraryAdapters,
            List<DataAdapter> dataAdapters) {
        this.spark = spark;
        this.modelAggregate = new ModelAdapterAggregator(modelAdapters);
        this.libraryAggregate = new LibraryAdapterAggregator(libraryAdapters);
        this.dataAggregate = new DataAdapterAggregator(dataAdapters);
    }

    @Override
    public Result visitContextDef(ContextDef node, Environment env) {
        return new ValueResult(node.getName());
    }

    @Override
    public Result visitExpressionDef(ExpressionDef node, Environment env) {
        var r = visitExpression(node.getExpression(), env);
        Dataset<Row> dsExpressionDef = match(visitExpression(node.getExpression(), env))
            .on(Column.class, c -> env.getContextFrame().select(col("_context_id"), c.alias("_value")))
            .on(Dataset.class, ds -> ds)
            .end();

        var dsAliasedExpressionDef = dsExpressionDef.alias(node.getName());

        env.putExpressionDef(node.getName(), dsAliasedExpressionDef);
        return dsAliasedExpressionDef;
    }

    @Override
    public Result visitLibrary(Library node, Environment env) {
        // var ds = spark.createDataset(List.of(1), Encoders.INT()).toDF();
        // env.addQuerySource("test", ds);

        // PROCESS: Usings
        // PROCESS: Includes
        // PROCESS: CodeSystems
        // PROCESS: Codes
        // PROCESS: Concepts
        // PROCESS: ValueSets
        // PROCESS: Parameters

        // PROCESS: Context
        String contextText = node.getContexts() != null
                ? node.getContexts().getDef().stream().map(n -> visitContextDef(n, env)).findFirst().get().toString()
                : "Unfiltered";
        env.setContext(Context.valueOf(contextText));

        if (env.getContext() == Context.Unfiltered) {
            var ds = spark.createDataset(List.of(-1), Encoders.INT()).select(col("value").alias("_context_id")).toDF();
            env.setContextFrame(ds);
        }

        // PROCESS: Statements
        if (node.getStatements().getDef() != null) {
            var r = node.getStatements().getDef().stream().map(n -> visitExpressionDef(n, env));
            var dsList = r.map(n -> (Dataset<Row>)n).toList();
            dsList.forEach(ds -> {
                Log.info(ds.toJSON().head());
            });
            return dsList;
        }

        return null;
    }

    @Override
    public Result visitTuple(Tuple node, Environment env) {
        var elements = node.getElement().stream().map(n -> visitTupleElement(n, env)).toList();
        var colList = elements.stream().filter(n -> n instanceof Column).map(n -> (Column)n).toList();
        var dsList = elements.stream().filter(n -> n instanceof Dataset).map(n -> (Dataset<Row>)n).toList();
        if (dsList.size() > 0) {
            throw new NotImplementedException("Not implemented");
        }
        return struct(colList.toArray(Column[]::new));
    }

    @Override
    public Result visitTupleElement(TupleElement node, Environment env) {
        return match(visitExpression(node.getValue(), env))
            .on(Column.class, c -> c.alias(node.getName()))
            .on(Dataset.class, d -> d.alias(node.getName()))   // TODO: Force Cardinality to One-to-One
            .end();
    }

    @Override
    public Result visitLiteral(Literal node, Environment env) {
        return lit(node.getValue());
    }

    // @Override
    // public Result visitList(org.hl7.elm.r1.List node, Environment env) {
    //     var cols = node.getElement().stream().map(n -> {
    //         return match(visitExpression(n, env))
    //             .on(Column.class, c -> c)
    //             .on(Dataset.class, d -> d.as(null))   // TODO: Force Cardinality to One-to-One
    //             .end();
    //     }).toList();

    //     return match(visitExpression(node.getValue(), env))
    //         .on(Column.class, c -> c.alias(node.getName()))
    //         .on(List.class, c -> {
    //             return array(((List<Column>)c).toArray(Column[]::new));
    //         })
    //         .on(Dataset.class, d -> d.alias(node.getName()))   // TODO: Force Cardinality to One-to-One
    //         .end();

    //     var items = node.getElement().stream().map(n -> visitExpression(n, env)).toList();
    //     array(((List<Column>)items).toArray(Column[]::new));
    // }
}