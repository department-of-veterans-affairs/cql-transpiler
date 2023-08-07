// package gov.va.sparkcql.encoder

// import au.csiro.pathling.encoders._
// import au.csiro.pathling.encoders.datatypes.R4DataTypeMappings
// // import au.csiro.pathling.library.PathlingContext
// import gov.va.sparkcql.TestBase
// import org.apache.spark.sql.functions._
// import gov.va.sparkcql.logging.Log
// import org.hl7.fhir.r4.model.{Encounter, Condition}
// //import java.util.Set
// import org.apache.spark.sql.Dataset
// import org.apache.spark.sql.Encoder
// import au.csiro.pathling.encoders.SchemaConverter
// import ca.uhn.fhir.context.FhirContext

// class EncoderTest extends TestBase {

//   val FHIR_CONTEXT = FhirContext.forR4()
//   val DATA_TYPE_MAPPINGS = new R4DataTypeMappings()

//   val NESTING_LEVEL = 5
//   //val ENCODERS_L0 = FhirEncoders.forR4().getOrCreate()
//   val DefaultResourceColumnName = "resource_data"
//   //val Encoders = FhirEncoders.forR4().withMaxNestingLevel(NESTING_LEVEL).getOrCreate()
//   val OPEN_TYPES = Set(
//       "boolean",
//       "canonical",
//       "code",
//       "date",
//       "dateTime",
//       "decimal",
//       "instant",
//       "integer",
//       "oid",
//       "positiveInt",
//       "string",
//       "time",
//       "unsignedInt",
//       "uri",
//       "url",
//       "Coding",
//       "Identifier"
//   )
//   val Encoders = FhirEncoders.forR4()
//     .withExtensionsEnabled(false)
//     .getOrCreate()

//   "An EncoderTest" should "decode FHIR R4 without failure" in {
//     import spark.implicits._
//     val bundle1 = getResource("/fhir/Aaron697_Brekke496_2fa15bc7-8866-461a-9000-f739e425860a.json")        
//     val bundles = Seq(bundle1)    

//     val resourceText = spark.read.json(bundles.toDS)
//       .select(explode(col("entry")).as("entry"))
//       .select(
//         to_json(struct(col("entry.resource.*"))).as(DefaultResourceColumnName),
//         col("entry.resource.resourceType").as("resourceType"))
//       .where(col("resourceType").equalTo("Encounter"))
//         .select(col(DefaultResourceColumnName))
//         .toDF()
    
//     val schemaConverter = new SchemaConverter(FHIR_CONTEXT, DATA_TYPE_MAPPINGS, EncoderConfig(NESTING_LEVEL, OPEN_TYPES, false))
//     val schema = schemaConverter.resourceSchema(classOf[Encounter])
    
//     //Log.warn(schema.treeString)
//     //Log.warn(schema.toDDL)
//     val encounters = Seq(getResource("/fhir/Encounters.json"))
//     implicit val encounterEncoder = Encoders.of(classOf[Encounter])
//     val df = spark.read.schema(schema).json(encounters.toDS())
//     Log.info(df)

//     val dfEncoded = df.as[Encounter]
//     Log.info(dfEncoded)

//     //val df = spark.createDataset(resourceText.collect().toSeq, conditionEncoder)
//     //val df: Dataset[Condition] = pathling.encodeBundle(bundlesDF, classOf[Condition], FhirMimeTypes.FHIR_JSON);
//   }

//   // it should "also encode" in {
//   //   import spark.implicits._
//   //   val bundle1 = getResource("/fhir/Aaron697_Brekke496_2fa15bc7-8866-461a-9000-f739e425860a.json")        
//   //   val bundles = Seq(bundle1)
//   //   val resourceText = spark.read.json(bundles.toDS)
//   //     .select(explode(col("entry")).as("entry"))
//   //     .select(
//   //       to_json(struct(col("entry.resource.*"))).as(DefaultResourceColumnName),
//   //       col("entry.resource.resourceType").as("resourceType"))
//   //     .where(col("resourceType").equalTo("Condition"))
//   //       .select(col(DefaultResourceColumnName))
//   //       .toDF()


//   //   val pc = PathlingContext.create(spark)
//   //   val patients = pc.encode(resourceText, "Condition")
//   // }
// }