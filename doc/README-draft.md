# Design Goals
- Offer a CQL engine implementation as a new Spark based language
- CQL evaluation should provide similar performance with other languages
- Keep external package dependencies to a minimum
- Enforce strong typing wherever possible
- Allow for greater interoperability between components written in SQL, Python, Scala, or CQL within the Spark ecosystem.

# Data Model
The Data Models required by CQL are strongly typed DataSets modeled through Case Classes which Spark can more naturally and efficiently process compared to Java Beans. Scala limits each Case Class to 254 fields but FHIR models should stay under this limitation considering they're based on compositions of multiple types each with their own 254 limit.

FHIR R4 Model
- Ignored
    - Extension and modifierExtension attributes
    - profile attributes
    - US Core constraints
- Required
    - Resource Id
    - Identifier systema and value
    - CodeableConcept coding
- Other Rules
    - Should list required or common fields first
    - Always list Element.id last
    - Added ResourceType to Resource
    - Strongly typed disjoint unions are not supported (e.g. Bundles). Those elements must be stored as text or avoided altogether.

*Currently only the FHIR R4 is targeted and many hardcoded assumptions are made in the code. Eventually, this should be refactored to externalize the model from the core evaluator. Perhaps even support a pluggable interface for user defined models.*