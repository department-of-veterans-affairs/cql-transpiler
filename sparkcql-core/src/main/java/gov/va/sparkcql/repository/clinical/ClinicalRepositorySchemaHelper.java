package gov.va.sparkcql.repository.clinical;

public final class ClinicalRepositorySchemaHelper {
    
    private ClinicalRepositorySchemaHelper() {
    }

    public static String resolveContextColumn(String contextSpecifier) {
        if (contextSpecifier.equals("Patient")) {
            return "patientCorrelationId";
        } else if (contextSpecifier.equals("Practitioner")) {
            return "practitionerCorrelationId";
        } else {
            throw new RuntimeException("Unsupported CQL context 'unfiltered'.");
        }
    }
}