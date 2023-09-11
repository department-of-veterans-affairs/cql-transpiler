package gov.va.sparkcql.pipeline.model;

import au.csiro.pathling.encoders.EncoderConfig;
import ca.uhn.fhir.context.FhirContext;
import gov.va.sparkcql.types.DataType;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.types.StructType;
import org.hl7.elm.r1.ContextDef;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import scala.Tuple2;

import au.csiro.pathling.encoders.FhirEncoders;
import au.csiro.pathling.encoders.datatypes.R4DataTypeMappings;
import au.csiro.pathling.encoders.SchemaConverter;

import java.util.List;

public class FhirModelAdapter implements ModelAdapter {

    private static final FhirContext fhirContext = FhirContext.forR4();
    private static final R4DataTypeMappings dataTypeMappings = new R4DataTypeMappings();
    private static final int maxNestingLevel = 5;
    private static final FhirEncoders fhirEncoders = FhirEncoders.forR4()
         .withExtensionsEnabled(false)
         .withMaxNestingLevel(maxNestingLevel)
         .getOrCreate();

    @Override
    public String getNamespaceUri() {
        return "http://hl7.org/fhir";
    }

    @Override
    public Object deserialize(DataType dataType, String json) {
        try {
            var typeMap = resolveTypeMap(dataType);
            var parser = fhirContext.newJsonParser();
            return parser.parseResource(asBaseResourceClass(typeMap._2), json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<IBaseResource> asBaseResourceClass(Class<?> clazz) {
        return (Class<IBaseResource>)clazz;
    }

    @Override
    public String serialize(Object entity) {
        try {
            FhirContext fhirContext = FhirContext.forR4();
            var parser = fhirContext.newJsonParser();
            return parser.encodeResourceToString((IBaseResource)entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tuple2<DataType, Class<?>>> supportedDataTypes() {
        return List.of(
                Tuple2.apply(new DataType(getNamespaceUri(), "AllergyIntolerance"), AllergyIntolerance.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "CarePlan"), CarePlan.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "CareTeam"), CareTeam.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Claim"), Claim.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Condition"), Condition.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Coverage"), Coverage.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Device"), Device.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "DiagnosticReport"), DiagnosticReport.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Encounter"), Encounter.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Goal"), Goal.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "ImagingStudy"), ImagingStudy.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Immunization"), Immunization.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Location"), Location.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Medication"), Medication.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "MedicationAdministration"), MedicationAdministration.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "MedicationRequest"), MedicationRequest.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Observation"), Observation.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Organization"), Organization.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Patient"), Patient.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Practitioner"), Practitioner.class),
                Tuple2.apply(new DataType(getNamespaceUri(), "Procedure"), Procedure.class)
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Encoder<T> getEncoder(DataType dataType) {
        var typeMap = resolveTypeMap(dataType);
        var clazz = asBaseResourceClass(typeMap._2);
        var expressionEncoder = fhirEncoders.of(clazz);
        return (Encoder<T>)expressionEncoder;
    }

    @Override
    public StructType getSchema(DataType dataType) {
        assertDataTypeIsSupported(dataType);
        var typeMap = resolveTypeMap(dataType);
        var clazz = asBaseResourceClass(typeMap._2);
        var schemaConverter = new SchemaConverter(fhirContext, dataTypeMappings, EncoderConfig.apply(maxNestingLevel, null, false));
        return schemaConverter.resourceSchema(clazz);
    }

    @Override
    public String getContextId(Object instance, ContextDef contextDef) {
        if (contextDef.getName().equals("Patient")) {

            // If it's not a FHIR resource at all.
            if (!Resource.class.isAssignableFrom(instance.getClass())) {
                return null;
            }

            // Resolve path based on type.
            switch (instance.getClass().getSimpleName()) {
                case "AllergyIntolerance":
                    return ((AllergyIntolerance) instance).getPatient().getId();
                case "CarePlan":
                    return getPatientId(((CarePlan) instance).getSubject().getResource());
                case "CareTeam":
                    return getPatientId(((CareTeam) instance).getSubject().getResource());
                case "Claim":
                    return getPatientId(((Claim) instance).getPatient().getResource());
                case "Condition":
                    return getPatientId(((Condition) instance).getSubject().getResource());
                case "Coverage":
                    return getPatientId(((Coverage) instance).getSubscriber().getResource());
                case "Device":
                    return ((Device) instance).getPatient().getId();
                case "DiagnosticReport":
                    return getPatientId(((DiagnosticReport) instance).getSubject().getResource());
                case "Encounter":
                    return getPatientId(((Encounter) instance).getSubject().getResource());
                case "Goal":
                    return getPatientId(((Goal) instance).getSubject().getResource());
                case "ImagingStudy":
                    return getPatientId(((ImagingStudy) instance).getSubject().getResource());
                case "Immunization":
                    return ((Immunization) instance).getPatient().getId();
                case "MedicationAdministration":
                    return getPatientId(((MedicationAdministration) instance).getSubject().getResource());
                case "MedicationRequest":
                    return getPatientId(((MedicationRequest) instance).getSubject().getResource());
                case "Observation":
                    return getPatientId(((Observation) instance).getSubject().getResource());
                case "Patient":
                    return ((Patient) instance).getId();
                case "Procedure":
                    return getPatientId(((Procedure) instance).getSubject().getResource());
                case "Location":
                case "Medication":
                case "Organization":
                case "Practitioner":
                    return null;
            }
        } else {
            throw new UnsupportedOperationException();
        }

        return null;
    }

    private String getPatientId(IBaseResource resource) {
        if (resource instanceof Patient) {
            var patient = (Patient)resource;
            return patient.getId();
        } else {
            return null;
        }
    }
}