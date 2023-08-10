package gov.va.sparkcql.executor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.cqframework.cql.elm.serializing.ElmLibraryReaderFactory;
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory;
import org.cqframework.cql.elm.serializing.LibraryWrapper;
import org.cqframework.cql.elm.serializing.jackson.ElmJsonMapper;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Retrieve;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.MapPartitionsFunction;

import gov.va.sparkcql.common.di.ServiceContext;
import gov.va.sparkcql.model.EvaluationResult;
import gov.va.sparkcql.model.Plan;

public class DefaultExecutor implements Executor {

    @Override
    public Dataset<EvaluationResult> execute(List<Library> libraries, Plan plan, Dataset<Row> clinicalData, Dataset<Row> terminologyData) {

        // Spark distributes our processing code to each executor node which 
        // requires serialization of all enclosed objects. The ELM does not 
        // implement the Serialization interface so we must provide manual serde
        // to primitive types and rehydrate those on each executor.

        try {
            // Serialize the ELM libraries.
            var libraryWriter = ElmLibraryWriterFactory.getWriter("application/elm+json");
            var libraryCollectionJson = libraries.stream().map(library -> {
                return libraryWriter.writeAsString(library);
            }).toList();

            // Serialize the Plan (which contains references to ELM Retrieve elements).
            var planJson = ElmJsonMapper.getMapper().writeValueAsString(plan);
            
            var results = clinicalData.mapPartitions((MapPartitionsFunction<Row, EvaluationResult>) row -> {

                // NOTE: Everything within mapPartitions is now running on the executor nodes.

                // Deserialize the ELM libraries
                var libraryReader = ElmLibraryReaderFactory.getReader("application/elm+json");
                var rehydratedLibraries = libraryCollectionJson.stream().map(json -> {
                    try {
                        return libraryReader.read(json);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }).toList();

                // Deserialize the Plan.
                var rehydratedPlan = ElmJsonMapper.getMapper().readValue(planJson, Plan.class);

                return new Iterator<EvaluationResult>() {

                    @Override
                    public boolean hasNext() {
                        return row.hasNext();
                    }

                    @Override
                    public EvaluationResult next() {
                        var nextRow = row.next();
                        return new EvaluationResult();
                    }
                    
                };
            }, Encoders.bean(EvaluationResult.class));
            
            return results;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
/*
    @Override
    public Map<String, Dataset<EvaluationResult>> execute(List<Library> libraries, Plan plan, Dataset<Row> clinicalData, Dataset<Row> terminologyData) {
        
        try {
            var engine = ServiceContext.createOne(Engine.class);
            
            var writer = new ObjectMapper().writer();
            //var librariesJson = writer.writeValueAsString(new LibraryCollection(libraries));
            
            var results = clinicalData.map((MapFunction<Row, EvaluationResult>) row -> {
                // var data = plan.getRetrievalOperations().stream()
                //     .collect(Collectors.toMap(
                //         r -> r.getRetrieve(),
                //         r -> (Object)row.getStruct(row.fieldIndex(r.getHashKey())).json()
                //     )
                // );
                //Map<Retrieve, Object> data = null;
                //var result = engine.evaluate(libraries, data, null);
                // var reader = new ObjectMapper().reader();
                // var libraryCollection = reader.readValue(librariesJson, LibraryCollection.class);

                var cls = engine.getClass();
                var x = engine.evaluate(null, null, null);
                //var y = libraries.size();
                var y = clinicalData.count();
                var result = new EvaluationResult();
                return result;
            }, Encoders.bean(EvaluationResult.class));
            results.show();
            
            return Map.of("test", results);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
*/
}