package gov.va.sparkcql.executor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;

import com.google.inject.Inject;

import org.apache.spark.api.java.function.MapPartitionsFunction;

import gov.va.sparkcql.entity.ExecutionResult;
import gov.va.sparkcql.entity.ExpressionReference;
import gov.va.sparkcql.entity.GenericTypeSpecifiedElement;
import gov.va.sparkcql.entity.LibraryCollection;
import gov.va.sparkcql.entity.Plan;
import gov.va.sparkcql.model.ModelAdapterResolver;

public class DefaultExecutor implements Executor {

    private ModelAdapterResolver modelAdapterResolver;
    private Engine engine;

    @Inject
    public DefaultExecutor(ModelAdapterResolver modelAdapterResolver, Engine engine) {
        this.modelAdapterResolver = modelAdapterResolver;
        this.engine = engine;
    }

    @Override
    public Dataset<ExecutionResult> execute(LibraryCollection libraryCollection, Plan plan, Dataset<Row> clinicalDs, Dataset<Row> terminologyDs) {

        // Spark distributes our processing code to each executor node, an operation requiring
        // serialization of all enclosed objects. The ELM does not implement the Serialization
        // interface. For those cases, wrap the unserializable object in a serializable one.
        // See Plan and LibraryCollection for examples.

        try {

            var results = clinicalDs.mapPartitions((MapPartitionsFunction<Row, ExecutionResult>) row -> {

                // NOTE: Everything within mapPartitions is now running on the executor nodes.
                // Any one-time initialization per partition (aka across several rows) should 
                // be performed here rather than within Iterator<EvaluationResult> below.
                
                return new Iterator<ExecutionResult>() {

                    @Override
                    public boolean hasNext() {
                        return row.hasNext();
                    }

                    @Override
                    public ExecutionResult next() {
                        // Iterate to the next context element for those defined within the partition.
                        var nextRow = row.next();
                        return processRow(nextRow, libraryCollection, plan);
                    }
                };
            }, Encoders.bean(ExecutionResult.class));
            
            return results;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private ExecutionResult processRow(Row row, LibraryCollection libraryCollection, Plan plan) {
        try {
            // Serialize clinical data into a key/value (Retrieve, Object) pair for lookup by the engine.
            var contextCorrelationId = row.getString(row.fieldIndex("contextCorrelationId"));
            var clinicalData = plan.getRetrievalOperations().stream()
                .collect(Collectors.toMap(
                    r -> r.getRetrieve(),
                    r -> {
                        var dataCol = row.getList(row.fieldIndex(r.getHashKey()));
                        return dataCol.stream().map(item -> {
                            var dataRow = (Row)item;
                            var dataJson = dataRow.json();
                            var dataType = r.getRetrieve().getDataType();
                            var modelAdapter = modelAdapterResolver.resolveNamespace(dataType);
                            var instance = modelAdapter.deserialize(dataType, dataJson);
                            return instance;
                        }).toList();
                    }
                )
            );

            // Execute this single context element using an injected CQL Engine.
            var engineResult = engine.evaluate(contextCorrelationId, libraryCollection, clinicalData, null);

            // Deserialize the results from the engine back into object and then StructType form.
            // TODO: Optimize serde to use encoders to avoid unnecessary intermediate serialization.
            //       which should increase performance considerably.
            
            // Pack up evaluated resources (resources used somewhere in the calculation) and
            // expression results (output of a CQL Expression Def) and return it.
            
            List<GenericTypeSpecifiedElement<String>> evaluatedResults = List.of();
            if (engineResult.getEvaluatedResources() != null) {
                evaluatedResults = engineResult.getEvaluatedResources().stream().map(t -> {
                    var modelAdapter = modelAdapterResolver.resolveType(t.getResultTypeName());
                    return new GenericTypeSpecifiedElement<String>()
                        .withResultTypeName(t.getResultTypeName())
                        .withResultValue(modelAdapter.serialize(t.getResultValue()));
                }).toList();
            }

            Map<ExpressionReference, GenericTypeSpecifiedElement<String>> expressionResults = Map.of();
            if (engineResult.getExpressionResults() != null) {
                expressionResults = engineResult.getExpressionResults().entrySet().stream().map(t -> {
                    var modelAdapter = modelAdapterResolver.resolveType(t.getValue().getResultTypeName());
                    var e = new GenericTypeSpecifiedElement<String>()
                        .withResultTypeName(t.getValue().getResultTypeName())
                        .withResultValue(modelAdapter.serialize(t.getValue().getResultValue()));
                    return Map.entry(t.getKey(), e);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
            
            var executionResult = new ExecutionResult();
            executionResult.setEvaluatedResources(evaluatedResults);
            executionResult.setExpressionResults(expressionResults);
            return executionResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }        
    }
}