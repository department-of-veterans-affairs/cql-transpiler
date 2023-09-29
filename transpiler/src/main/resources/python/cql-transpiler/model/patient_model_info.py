from model.model_info import ModelInfo

class PatientModelInfo(ModelInfo):
    
    def getIdColumnName(self) -> str:
        return 'patientID'
    
    def getTypeName(self) -> str:
        return 'patient'