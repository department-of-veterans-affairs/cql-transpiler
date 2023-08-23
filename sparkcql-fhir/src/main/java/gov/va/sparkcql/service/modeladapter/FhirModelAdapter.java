package gov.va.sparkcql.service.modeladapter;

import java.util.Map;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.CarePlan;
import org.hl7.fhir.r4.model.CareTeam;
import org.hl7.fhir.r4.model.Claim;
import org.hl7.fhir.r4.model.Condition;
import org.hl7.fhir.r4.model.Coverage;
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Goal;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.Location;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Procedure;

import ca.uhn.fhir.context.FhirContext;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapter;
import gov.va.sparkcql.types.DataType;

public class FhirModelAdapter implements ModelAdapter {

    @Override
    public String getNamespaceUri() {
        return "http://hl7.org/fhir";
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object deserialize(DataType dataType, String json) {
        try {
            var clazz = (Class<IBaseResource>)getDataTypeToClassMapping().get(dataType);
            FhirContext fhirContext = FhirContext.forR4();
            var parser = fhirContext.newJsonParser();
            var parsed = parser.parseResource(clazz, json);
            return parsed;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String serialize(Object entity) {
        try {
            FhirContext fhirContext = FhirContext.forR4();
            var parser = fhirContext.newJsonParser();
            var json = parser.encodeResourceToString((IBaseResource)entity);
            return json;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<DataType, Class<?>> getDataTypeToClassMapping() {
        return Map.ofEntries(
            Map.entry(new DataType(getNamespaceUri(), "AllergyIntolerance"), AllergyIntolerance.class),
            Map.entry(new DataType(getNamespaceUri(), "CarePlan"), CarePlan.class),
            Map.entry(new DataType(getNamespaceUri(), "CareTeam"), CareTeam.class),
            Map.entry(new DataType(getNamespaceUri(), "Claim"), Claim.class),
            Map.entry(new DataType(getNamespaceUri(), "Condition"), Condition.class),
            Map.entry(new DataType(getNamespaceUri(), "Coverage"), Coverage.class),
            Map.entry(new DataType(getNamespaceUri(), "Device"), Device.class),
            Map.entry(new DataType(getNamespaceUri(), "DiagnosticReport"), DiagnosticReport.class),
            Map.entry(new DataType(getNamespaceUri(), "Encounter"), Encounter.class),
            Map.entry(new DataType(getNamespaceUri(), "Goal"), Goal.class),
            Map.entry(new DataType(getNamespaceUri(), "ImagingStudy"), ImagingStudy.class),
            Map.entry(new DataType(getNamespaceUri(), "Immunization"), Immunization.class),
            Map.entry(new DataType(getNamespaceUri(), "Location"), Location.class),
            Map.entry(new DataType(getNamespaceUri(), "MedicationRequest"), MedicationRequest.class),
            Map.entry(new DataType(getNamespaceUri(), "Observation"), Observation.class),
            Map.entry(new DataType(getNamespaceUri(), "Organization"), Organization.class),
            Map.entry(new DataType(getNamespaceUri(), "Patient"), Patient.class),
            Map.entry(new DataType(getNamespaceUri(), "Practitioner"), Practitioner.class),
            Map.entry(new DataType(getNamespaceUri(), "Procedure"), Procedure.class)
        );
    }
}