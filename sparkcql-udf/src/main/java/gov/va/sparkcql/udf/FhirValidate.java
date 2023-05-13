package gov.va.sparkcql.udf;

import org.apache.spark.sql.api.java.UDF1;
import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;

public class FhirValidate implements UDF1<String, String> {

    @Override
    public String call(String instanceJson) throws Exception {
        var ctx = FhirContext.forR4();

        // Ask the context for a validator
        var validator = ctx.newValidator();

        // Create a validation module and register it
        var module = new FhirInstanceValidator(ctx);
        validator.registerValidatorModule(module);

        // Validate the FHIR resource passed into the UDF
        String output = "";
        var result = validator.validateWithResult(instanceJson).getMessages();
        for (var item : result) {
            output += item.getLocationString() + ": " + item.getMessage() + ";";
        }

        // Return error messages or an empty string if successful.
        return output;        
    }
}