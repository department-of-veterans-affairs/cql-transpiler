// package gov.va.fast.evaluator;

// import java.io.IOException;
// import java.io.StringReader;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.Map;

// import org.apache.spark.sql.SparkSession;
// import org.apache.spark.sql.Dataset;
// import org.apache.spark.sql.Encoder;
// import org.apache.spark.sql.Encoders;
// import org.apache.spark.sql.Row;
// import static org.apache.spark.sql.functions.*;

// import org.hl7.fhir.r4.model.Library;
// import org.hl7.fhir.Identifier;
// import org.hl7.elm.r1.VersionedIdentifier;

// import ca.uhn.fhir.context.FhirContext;
// import gov.va.fast.common.util.Resources;

// public class MockResourceProvider implements ResourceProvider {
//     SparkSession spark;
//     Map<VersionedIdentifier, Dataset<Row>> resources;

//     public MockResourceProvider(SparkSession spark, String[] elmPath) throws Exception {
//         this.spark = spark;
//         resources = new HashMap<VersionedIdentifier, Dataset<Row>>();
        
//         // Mount all the sample data we have available. Since dataframes are lazily evaluated,
//         // there's no penalty to mounting everything since it will only be evaluated if used.
//         mountFhirR4SampleData();

//         // Attach the ELMs as Library Entries
//         mountLibrary(spark, elmPath);
//     }

//     @Override
//     public Dataset<Row> resolve(VersionedIdentifier id) {
//         throw new UnsupportedOperationException("Unimplemented method 'resolve'");
//     }

//     private void mountLibrary(SparkSession spark, String[] elmPath) throws Exception {
//         var ctx = FhirContext.forR4();

//         // Load all specified ELMs from disk
//         var libraryJsonItems = new ArrayList<String>();
//         for (String path : elmPath) {
//             var jsonElm = Resources.load(path);
//             libraryJsonItems.add(jsonElm);
//             var library = new Library();
//             library.setLocalId("foo");
//             var parser = ctx.newJsonParser();
//             String serialized = parser.encodeResourceToString(library);
//             System.out.println(serialized);
//             // TODO: CONVERT TO BINARY 64
//             // TODO: READ ID, TEMPLATE, COMBINE RIGHT HERE
//         }

//         // Load the Library resource template which will be used to construct the full resource listing
//         var template = Resources.load("fhir/template/Library.json");
        
//         // var dfElm = spark.read().json(spark.createDataset(libraryItems, Encoders.STRING()));
//         // var schema = dfElm.schema();
//         // System.out.println(schema.toDDL());
//         // dfElm.printSchema();
        
//         // var df = spark.emptyDataFrame();
//         // df = df.select(
//         //     lit("123").as("RowID"),
//         //     lit("ABC").as("ResourceData"));
//         // df.show();
//     }
    
//     private void mountFhirR4SampleData() {
//         try {
//             // Load a few synthetic bundles into a Dataframe
//             var jsonBundle = Arrays.asList(
//                     Resources.load("fhir/bundle/Aaron697_Brekke496_2fa15bc7-8866-461a-9000-f739e425860a.json"),
//                     Resources.load("fhir/bundle/Agnes294_Jenkins714_185d26ad-fb9f-40ae-afb0-94d72827d887.json"),
//                     Resources.load("fhir/bundle/Ali918_Stokes453_f5aa3408-57b3-4c05-a1d3-e4511b4be50e.json"),
//                     Resources.load("fhir/bundle/Andreas188_Dare640_f7f63ca8-d282-4520-9a68-3177e2a5db6f.json"),
//                     Resources.load("fhir/bundle/Clorinda310_Abshire638_9138db90-a8bf-465f-9b5d-16f8fd5749da.json"),
//                     Resources.load("fhir/bundle/Despina962_Collier206_c3127664-ea4c-482c-953a-92f24da84bba.json"),
//                     Resources.load("fhir/bundle/Erline657_Casper496_f8329bff-a048-4054-8a6b-208f54a2a330.json"),
//                     Resources.load("fhir/bundle/Jenise920_Stokes453_02b253ec-2379-4334-87fa-b959e7838ed4.json"),
//                     Resources.load("fhir/bundle/Kent912_Ruecker817_f4b034c1-9e82-4e3a-8eda-96d1b938692f.json"),
//                     Resources.load("fhir/bundle/Leopoldo762_Reynolds644_ef29d12a-a4a6-4b49-b100-66efa3a01987.json"),
//                     Resources.load("fhir/bundle/Micheal721_Jacobi462_19b766a8-ed5e-4c16-8f83-435abef893c4.json"),
//                     Resources.load("fhir/bundle/Monserrate4_Carter549_aa465d13-1030-4fd4-a352-a1c615ea6df2.json"),
//                     Resources.load("fhir/bundle/Omega729_Grimes165_822c2111-815a-4710-85d8-4edc2b621576.json"),
//                     Resources.load("fhir/bundle/Roy364_Kuhic920_59871528-6534-47da-bf5e-6ba844ec46f7.json"),
//                     Resources.load("fhir/bundle/Sina65_Wolff180_582b89e2-30d8-44fb-bb96-03957b2ec7c2.json"),
//                     Resources.load("fhir/bundle/Truman805_Durgan499_277bea41-b704-4be3-972a-4feee4e2712b.json"));

//             var dfBundle = spark.read().json(spark.createDataset(jsonBundle, Encoders.STRING()));
//             // dfBundle.createOrReplaceTempView("SampleBundle");

//             // Explode bundles into their entries so there's one row per entry.
//             var dfEntries = dfBundle.select(explode(col("entry")).as("entry"));
           
//             // Transform entries into resource specific views conforming to the required input schema.
//             mountFhirR4DataType(dfEntries, "AllergyIntolerance", null, null, null);
//             mountFhirR4DataType(dfEntries, "CarePlan", null, null, null);
//             mountFhirR4DataType(dfEntries, "CareTeam", null, null, null);
//             mountFhirR4DataType(dfEntries, "Claim", null, null, null);
//             mountFhirR4DataType(dfEntries, "Condition", "code.coding[0].code", "onsetDateTime", "onsetDateTime");
//             mountFhirR4DataType(dfEntries, "Device", null, null, null);
//             mountFhirR4DataType(dfEntries, "DiagnosticReport", null, null, null);
//             mountFhirR4DataType(dfEntries, "Encounter", "class.code", "period.start", "period.end");
//             mountFhirR4DataType(dfEntries, "ExplanationOfBenefit", null, null, null);
//             mountFhirR4DataType(dfEntries, "Goal", null, null, null);
//             mountFhirR4DataType(dfEntries, "ImagingStudy", null, null, null);
//             mountFhirR4DataType(dfEntries, "Immunization", null, null, null);
//             mountFhirR4DataType(dfEntries, "MedicationRequest", null, null, null);
//             mountFhirR4DataType(dfEntries, "Organization", null, null, null);
//             mountFhirR4DataType(dfEntries, "Procedure", null, null, null);
//             mountFhirR4DataType(dfEntries, "Patient", null, null, null);
//             mountFhirR4DataType(dfEntries, "Practitioner", null, null, null);
//         } catch (Exception ex) {
//             // Avoid checked exception since this is Test.
//             throw new RuntimeException(ex);
//         }
//     }

//     private void mountFhirR4DataType(Dataset<Row> dfEntries, String dataType, String primaryCodePath, String primaryStartDate, String primaryEndDate) {
//         // Prefix the correct column path for the primary indexes. If omitted, use NULL.
//         primaryCodePath = primaryCodePath != null ? "entry.resource." + primaryCodePath : "null";
//         primaryStartDate = primaryStartDate != null ? "entry.resource." + primaryStartDate : "null";
//         primaryEndDate = primaryEndDate != null ? "entry.resource." + primaryEndDate : "null";

//         // Project entries into a new FHIR R4 resource matching the expected schema for the evaluator. Use
//         // the primary code, start, and end dates (if provided) as indexes to mimic a runtime environment.
//         var dfDataType = dfEntries
//             .withColumn("RowID", expr("uuid()"))
//             .withColumn("IndexPrimaryCodePath", expr(primaryCodePath))
//             .withColumn("IndexPrimaryStartDate", expr(primaryStartDate))
//             .withColumn("IndexPrimaryEndDate", expr(primaryEndDate))
//             .withColumn("ResourceData", expr("STRUCT(*)"))
//             .drop("entry")
//             .where(expr("entry.resource.resourceType").equalTo(dataType));
 
//         // Define a Versioned Identifier for this data type
//         var dataTypeId = ModelResolver.resolveDataTypeIdentifier(dataType, "http://hl7.org/fhir");        

//         // Instead of mounting the dataframe as a "stateful" view, record it within a
//         // local registry (map) so it can be looked up later.
//         resources.put(dataTypeId, dfDataType);
//     }
// }