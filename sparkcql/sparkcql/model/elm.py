from typing import TypedDict as _TypedDict

class VersionedIdentifier(_TypedDict):
    id: str
    version: str
    system: str

# FHIR Versioning Rules (https://build.fhir.org/versioning.html, https://build.fhir.org/versions.html)
# FHIR Version Agnostic (when under rules of CapabilityStatement)
# http://hl7.org/fhir

# FHIR Version Specific (outside any rule)
# http://hl7.org/fhir/3.0/StructureDefinition/Patient
# http://hl7.org/fhir/4.0/StructureDefinition/Patient
# http://hl7.org/fhir/4.3/StructureDefinition/Patient
# http://hl7.org/fhir/5.0/StructureDefinition/Patient

__all__ = ["VersionedIdentifier"]