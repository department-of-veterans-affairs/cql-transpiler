package gov.va.sparkcql.pipeline.retriever;

import ca.uhn.fhir.context.FhirContext;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.io.AssetFolder;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.hl7.fhir.r4.model.Bundle;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BundleRetriever implements Retriever {

    private SparkSession spark;

    private List<Bundle> bundles;

    public BundleRetriever(SparkFactory sparkFactory, AssetFolder bundles) {
        this.spark = sparkFactory.create();
        loadResourceBundles(bundles);
    }

    @Override
    public JavaRDD<Object> retrieve(Retrieval retrieval, ModelAdapterSet modelAdapterSet) {
        var modelAdapter = modelAdapterSet.forType(retrieval.getDataType());
        var typeMap = modelAdapter.resolveTypeMap(retrieval.getDataType());

        var entries = bundles.stream().flatMap(b -> {
            return b.getEntry().stream().map(e -> {
                var resource = e.getResource();
                if (resource.getClass().equals(typeMap._2)) {
                    return resource;
                } else {
                    return null;
                }
            });
        }).filter(Objects::nonNull);

        var sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        return sc.parallelize(entries.collect(Collectors.toList()));
    }

    private void loadResourceBundles(AssetFolder bundleAsset) {
        // Stream the bundles stored as resources files.
        var contents = bundleAsset.read();

        // For each one, load it into a HAPI bundle structure.
        var ctx = FhirContext.forR4();
        var parser = ctx.newJsonParser();
        bundles = contents.stream().map(content -> parser.parseResource(Bundle.class, content))
                .collect(Collectors.toList());
    }
}