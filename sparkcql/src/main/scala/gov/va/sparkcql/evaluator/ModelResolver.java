// package gov.va.fast.evaluator;

// import org.hl7.elm.r1.VersionedIdentifier;

// public final class ModelResolver {
//     private ModelResolver() {
//     }

//     public static VersionedIdentifier resolveDataTypeIdentifier(String dataTypeFQN) {
//         var part = dataTypeFQN.split("}");       
//         return resolveDataTypeIdentifier(part[1].toLowerCase(), part[0].substring(1, part[0].length()).toLowerCase());
//     }

//     public static VersionedIdentifier resolveDataTypeIdentifier(String dataType, String system) {
//         var dataTypeId = new VersionedIdentifier();
//         dataTypeId.setId(dataType);
//         dataTypeId.setSystem(system);
//         return dataTypeId;
//     }

//     // public String resolveDataTypeTo(VersionedIdentifier dataTypeId) {
//     //     var parts = dataTypeFQN.split("}");
//     //     var model = parts[0].substring(1, parts[0].length()).toLowerCase();

//     //     switch (model) {
//     //         case "http://hl7.org/fhir": return String.format("fhir_%s", parts[1]).toLowerCase();
//     //     }
        
//     //     throw new RuntimeException("Unknown data type " + dataTypeFQN);
//     // }
// }