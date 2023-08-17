package gov.va.sparkcql.adapter;
// package gov.va.sparkcql.fhir;

// import java.util.List;
// import java.util.Set;

// import javax.xml.namespace.QName;

// import org.apache.spark.sql.types.StructType;

// import au.csiro.pathling.encoders.EncoderConfig;
// import au.csiro.pathling.encoders.FhirEncoders;
// import au.csiro.pathling.encoders.SchemaConverter;
// import au.csiro.pathling.encoders.datatypes.R4DataTypeMappings;
// import ca.uhn.fhir.context.FhirContext;
// import gov.va.sparkcql.adapter.model.ModelAdapter;

// public class FhirModelAdapter implements ModelAdapter {

//     protected FhirContext fhirContext = FhirContext.forR4();
//     protected R4DataTypeMappings dataTypeMappings = new R4DataTypeMappings();
//     protected int maxNestingLevel = 5;

//     protected FhirEncoders encoders = FhirEncoders.forR4()
//         .withExtensionsEnabled(false)
//         .getOrCreate();

//     protected Set<String> openTypes = Set.of(
//         // "boolean",
//         // "canonical",
//         // "code",
//         // "date",
//         // "dateTime",
//         // "decimal",
//         // "instant",
//         // "integer",
//         // "oid",
//         // "positiveInt",
//         // "string",
//         // "time",
//         // "unsignedInt",
//         // "uri",
//         // "url",
//         // "Coding",
//         // "Identifier"
//     );


//     @Override
//     public List<QName> supportedDataTypes() {
//         return List.of(
//             new QName(namespaceUri(), "AllergyIntolerance"),
//             new QName(namespaceUri(), "CarePlan"),
//             new QName(namespaceUri(), "CareTeam"),
//             new QName(namespaceUri(), "Claim"),
//             new QName(namespaceUri(), "Condition"),
//             new QName(namespaceUri(), "Coverage"),
//             new QName(namespaceUri(), "Device"),
//             new QName(namespaceUri(), "DiagnosticReport"),
//             new QName(namespaceUri(), "Encounter"),
//             new QName(namespaceUri(), "Goal"),
//             new QName(namespaceUri(), "ImagingStudy"),
//             new QName(namespaceUri(), "Immunization"),
//             new QName(namespaceUri(), "Location"),
//             new QName(namespaceUri(), "MedicationRequest"),
//             new QName(namespaceUri(), "Observation"),
//             new QName(namespaceUri(), "Organization"),
//             new QName(namespaceUri(), "Patient"),
//             new QName(namespaceUri(), "Practitioner"),
//             new QName(namespaceUri(), "Procedure")
//         );
//     }

//     protected void assertDataTypeIsSupported(QName dataType) {
//         if (!supportedDataTypes().contains(dataType)) {
//             throw new RuntimeException("Unsupported data type '" + dataType.toString() + "'.");
//         }
//     }

//     @Override
//     public StructType schemaOf(QName dataType) {
//         assertDataTypeIsSupported(dataType);
//         var schemaConverter = new SchemaConverter(fhirContext, dataTypeMappings, EncoderConfig.apply(maxNestingLevel, null, false));
//         var schema = schemaConverter.resourceSchema(mapDataTypeToClass(dataType));
//         return schema;
//     }

//     @SuppressWarnings("unchecked")
//     protected <T> Class<T> mapDataTypeToClass(QName dataType) {
//         assertDataTypeIsSupported(dataType);
//         switch (dataType.getLocalPart()) {
//             case "AllergyIntolerance": return (Class<T>)org.hl7.fhir.r4.model.AllergyIntolerance.class;
//             case "CarePlan":
//             case "CareTeam":
//             case "Claim":
//             case "Condition": return (Class<T>)org.hl7.fhir.r4.model.Condition.class;
//             case "Coverage":
//             case "Device":
//             case "DiagnosticReport":
//             case "Encounter": return (Class<T>)org.hl7.fhir.r4.model.Encounter.class;
//             case "Goal":
//             case "ImagingStudy":
//             case "Immunization":
//             case "Location":
//             case "MedicationRequest":
//             case "Observation":
//             case "Organization":
//             case "Patient":
//             case "Practitioner":
//             case "Procedure":
//             default:
//                 throw new RuntimeException("Unexpected data type '" + dataType.toString() + "'.");
//         }
//     }

//     @Override
//     public String namespaceUri() {
//         return "http://hl7.org/fhir";
//     }
// }