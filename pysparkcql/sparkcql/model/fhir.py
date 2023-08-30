from datetime import datetime as _datetime
from typing import TypedDict as _TypedDict

# FHIR Versioning Rules (https://build.fhir.org/versioning.html, https://build.fhir.org/versions.html)
# FHIR Version Agnostic (when under rules of CapabilityStatement)
# http://hl7.org/fhir

# FHIR Version Specific (outside any rule)
# http://hl7.org/fhir/3.0/StructureDefinition/Patient
# http://hl7.org/fhir/4.0/StructureDefinition/Patient
# http://hl7.org/fhir/4.3/StructureDefinition/Patient
# http://hl7.org/fhir/5.0/StructureDefinition/Patient

# Primitive type aliases to be consistent with FHIR naming
code = str
uri = str
string = str
id = str
instant = _datetime
xhtml = str

# Complex base types
class Base(_TypedDict):
    pass

class Element(Base):
    id: string

class Coding(Element):
    system: uri
    version: string
    code: code
    display: string
    userSelected: string

class CodeableConcept(Element):
    coding: list[Coding]
    text: string

class Identifier(Element):
    use: string
    type: CodeableConcept
    system: uri
    value: string
    period: object

class Narrative(Element):
    status: code
    div: xhtml

class Meta(Element):
    versionId: id
    lastUpdated: instant
    source: uri
    # profile
    security: list[Coding]
    tag: list[Coding]

class Resource(Base):
    id: id
    meta: Meta
    implicitRules: uri
    language: code

class DomainResource(Resource):
    text: Narrative
    contained: list[Resource]

# Condition: "primaryCodePath": "code.coding[0].code",
# Encounter "primaryCodePath": "class.code",