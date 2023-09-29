from model.model_info import ModelInfo

class EncounterModelInfo(ModelInfo):
    
    def getIdColumnName(self) -> str:
        return 'encounterID'
    
    def getTypeName(self) -> str:
        return 'encounter'