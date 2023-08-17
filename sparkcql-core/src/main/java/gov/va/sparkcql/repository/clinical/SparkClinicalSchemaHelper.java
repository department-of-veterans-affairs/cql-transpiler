package gov.va.sparkcql.repository.clinical;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.types.StructType;

import gov.va.sparkcql.log.Log;
import gov.va.sparkcql.types.DataType;

public final class SparkClinicalSchemaHelper {

    /**
     * A mismatch between the table and canonical schemas could be caused by:
     *   a. Table defines a superset of canonical
     *   b. Table defines a subset of canonical
     *   c. Both (a) and (b)
     * A mismatch may be entirely appropriate depending on the intention. Implementors are
     * allowed to define additional attributes in their tables for their own purposes. Additionally, 
     * not all FHIR attributes will be relevant for all implementations. Therefore, only
     * check the intersection of critical attributes between the schemas.
     * 
     * @param tableSchema
     * @param canonicalSchema
     * @param dataType
     */
    public static void validateSchema(StructType tableSchema, StructType canonicalSchema, DataType dataType) {
         
        if (!tableSchema.toDDL().equals(canonicalSchema.toDDL())) {
            Log.warn("Actual and Expected schemas for type " + dataType.getName() + " differ.");
        }

        validateRequiredElements(tableSchema, dataType);
    }

    public static void validateRequiredElements(StructType tableSchema, DataType dataType) {
        // Ensure critical container level columns are present.
        var missingColumns = new ArrayList<String>();
        var requiredColumns = List.of("patientCorrelationId", "practictionerCorrelationId", "primaryCode", "primaryStartDate", "primaryEndDate", "dataType", "data");
        for (var column: requiredColumns) {
            if (tableSchema.getFieldIndex(column).isEmpty())
                missingColumns.add(column);
        }
        if (missingColumns.size() > 0) {
            var missingColumnsMsg = String.join(", ", missingColumns);
            throw new RuntimeException("Table is missing the following container level attributes: " + missingColumnsMsg + ".");
        }
    }
}