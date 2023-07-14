package gov.va.sparkcql.synthetic;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.explode;
import static org.apache.spark.sql.functions.from_json;
import static org.apache.spark.sql.functions.struct;
import static org.apache.spark.sql.functions.to_json;

import javax.xml.namespace.QName;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import gov.va.sparkcql.adapter.data.DataAdapter;
import gov.va.sparkcql.fhir.FhirModelAdapter;

public class SyntheaSourceAdapter implements DataAdapter {

    protected static final String TABLE_PREFIX = "synthea";
    protected static final String DEFAULT_RESOURCE_COLUMN = "resource_data";

    protected SparkSession spark;
    protected PopulationSize size;
    protected Dataset<Row> dsResources;
    protected FhirModelAdapter fhirAdapter = new FhirModelAdapter();

    public SyntheaSourceAdapter(SparkSession spark, PopulationSize size) {
        this.size = size;
        if (this.size != PopulationSize.PopulationSizeNone) {
            this.spark = spark;
            var bundles = new SyntheaDataLoader().loadBundles(size);
            var dsTextBundles = spark.createDataset(bundles, Encoders.STRING());
            var dsBundles = spark.read().json(dsTextBundles);
            this.dsResources = dsBundles
                .select(explode(col("entry")).as("entry"))
                .select(
                    to_json(struct(col("entry.resource.*"))).as(DEFAULT_RESOURCE_COLUMN),
                    col("entry.resource.resourceType").as("resourceType"));
        } else {
            // Returning synthetic data must always be explicit as a failsafe in case this
            // component makes its way into a production. The default should be disabled.
        }
    }

    @Override
    public Boolean isDataTypeDefined(QName dataType) {
        switch (dataType.toString()) {
            // Synthea Supported Types
            case "{http://hl7.org/fhir}AllergyIntolerance":
            case "{http://hl7.org/fhir}CarePlan":
            case "{http://hl7.org/fhir}CareTeam":
            case "{http://hl7.org/fhir}Claim":
            case "{http://hl7.org/fhir}Condition":
            case "{http://hl7.org/fhir}Coverage":
            case "{http://hl7.org/fhir}Device":
            case "{http://hl7.org/fhir}DiagnosticReport":
            case "{http://hl7.org/fhir}Encounter":
            case "{http://hl7.org/fhir}Goal":
            case "{http://hl7.org/fhir}ImagingStudy":
            case "{http://hl7.org/fhir}Immunization":
            case "{http://hl7.org/fhir}Location":
            case "{http://hl7.org/fhir}MedicationRequest":
            case "{http://hl7.org/fhir}Observation":
            case "{http://hl7.org/fhir}Organization":
            case "{http://hl7.org/fhir}Patient":
            case "{http://hl7.org/fhir}Practitioner":
            case "{http://hl7.org/fhir}Procedure":
                return true;
            default:
                return false;
        }
    }

    @Override
    public Dataset<Row> acquireData(QName dataType) {
        if (this.size != PopulationSize.PopulationSizeNone) {
            var resourceType = dataType.getLocalPart();
            var schema = fhirAdapter.schemaOf(dataType);
            var dsResource = dsResources
                .where(col("resourceType").equalTo(resourceType))
                .select(from_json(col(DEFAULT_RESOURCE_COLUMN), schema).as("instance"))
                .select(col("instance.*"));
            return dsResource;
        } else {
            return null;
        }
    }
}