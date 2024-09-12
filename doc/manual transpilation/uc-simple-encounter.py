# FILE: UC_Simple_Encounter__1_0.py

from pyspark.sql.functions import col

class UCSimpleEncounter_v1_0(CqlLibrary):
    
    VALUESET_EMERGENCY_DEPARTMENT_VISIT = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292'

    def library_identifier(self):
        return {
            "id": "UC_Simple_Encounter",
            "version": "1.0"
        }
        
    # define "ED Encounter":
    #   ["Encounter, Performed": "Emergency Department Visit"] E
    #   where E.relevantPeriod ends during day of "Measurement Period"
    def ed_encounter(self, pop: CqlDataFrame):
        return pop \
            .join(
                self.retrieve("QDM", "5.6", "Encounter, Performed").alias("E"),
                col('POP.patient_id') == col('E.patient_id')
            ) \
            .filter(col("E.code").ismemberof("urn:oid:2.16.840.1.113883.3.117.1.7.1.292")) \
            .filter(col("E.endDate").ge(self.parameters["Measurement Period".low])) \
            .filter(col("E.endDate").lt(date_add(self.parameters["Measurement Period".high], 1)))