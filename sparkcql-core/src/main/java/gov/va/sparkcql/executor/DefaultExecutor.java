package gov.va.sparkcql.executor;

import java.util.Iterator;
import java.util.stream.Collectors;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;

import org.apache.spark.api.java.function.MapPartitionsFunction;

import gov.va.sparkcql.common.di.ServiceContext;
import gov.va.sparkcql.model.EvaluationResult;
import gov.va.sparkcql.model.LibraryCollection;
import gov.va.sparkcql.model.Plan;

public class DefaultExecutor implements Executor {

    @Override
    public Dataset<EvaluationResult> execute(LibraryCollection libraryCollection, Plan plan, Dataset<Row> clinicalDs, Dataset<Row> terminologyDs) {

        // Spark distributes our processing code to each executor node, an operation requiring
        // serialization of all enclosed objects. The ELM does not implement the Serialization
        // interface. For those cases, wrap the unserializable object in a serializable one.
        // See Plan and LibraryCollection for examples.

        try {

            var engine = ServiceContext.createOne(Engine.class);

            var results = clinicalDs.mapPartitions((MapPartitionsFunction<Row, EvaluationResult>) row -> {

                // NOTE: Everything within mapPartitions is now running on the executor nodes.
                // Any one-time initialization per partition (across several rows) should be
                // performed here rather than within Iterator<EvaluationResult> below.
                
                return new Iterator<EvaluationResult>() {

                    @Override
                    public boolean hasNext() {
                        return row.hasNext();
                    }

                    @Override
                    public EvaluationResult next() {
                        var nextRow = row.next();

                        try {
                            var clinicalData = plan.getRetrievalOperations().stream()
                                .collect(Collectors.toMap(
                                    r -> r.getRetrieve(),
                                    r -> {
                                        //(Object)nextRow.getStruct(nextRow.fieldIndex(r.getHashKey())).json()
                                        // var a = (scala.collection.mutable.WrappedArray<?>)nextRow.getAs(r.getHashKey());
                                        // var c = (Row)a.array();
                                        var dataCol = nextRow.getList(nextRow.fieldIndex(r.getHashKey()));
                                        var items = dataCol.stream().map(item -> {
                                            var dataRow = (Row)item;
                                            return dataRow.json();      // TODO: Deserialize using a model adapter
                                        }).toList();
                                        
                                        return (Object)items;
                                    }
                                )
                            );

                            // TODO: May need a different result type from engine than from executor to deal w/ serialization differences.
                            var result = engine.evaluate(libraryCollection, clinicalData, null);
                            // TODO: Serialize using a model adapter
                            return result;
                        } catch (Exception ex) {
                            return new EvaluationResult();
                        }
                    }
                };
            }, Encoders.bean(EvaluationResult.class));
            
            return results;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}