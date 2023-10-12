from model.model_info import ModelInfo

class EncounterModel(ModelInfo):
    
    def getIdColumnName(self) -> str:
        return 'encounterID'
    
    def getName(self) -> str:
        return 'Encounter'