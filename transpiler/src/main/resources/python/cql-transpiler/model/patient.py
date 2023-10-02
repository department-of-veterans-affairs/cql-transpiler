from model.model_info import ModelInfo

class Patient(ModelInfo):
    
    def getIdColumnName(self) -> str:
        return 'patientID'

    def getName(self) -> str:
        return '?'