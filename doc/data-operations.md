# Data Operations

TODO:
- Context Correlation or Correlation DS
- Indexes and predicates (how Spark tables)
- "Cardinality is always in reference to context subject"

Although `Practitioner` and `Unfiltered` are both valid CQL contexts, for simplicity Patient will be the default `context` for the remainder of this document.
Many of the models and CQL expressions are deliberately shortened for the sake of brevity and may not constitute a valid model or CQL.

## System Columns
At times, special columns must be introduced to track and correlate system state. These columns will always be prefixed with an underscore and dropped prior to returning a translated dataset.
- _context_id
- _value

## Context Correlation Framing
Setting the CQL `context` establishes an implicit dataset iterating over the eligible population, or subpopulation in the case of an incremental refresh. This dataset is called the *Context Frame* in SparkCQL and contains a single identifier used to correlate data across all datasets for any context element (e.g. patient identifier). 

```json
[
    {
        "_context_id": 1,
    },
    {
        "_context_id": 2,
    }
]
```

All `ExpressionDef` statements use *Context Frame* as a basis such that any new data elements are joined against it. The join itself serves two purposes:
1. Constrains available data for the current context element.
2. Acts as an iterator ensuring all and only valid elements are processed.

Since *Context Frame* is critical to performance and relatively small, it is distributed across all executors to avoid shuffling.

### Context Data Type
The `context` introduces a data model representing the specified context subject and is made available within any definition. A reference to the context subject will implicitly join its data type to the context frame. For instance, the CQL `define "FemalePatients": Patient.gender in "Female Administrative Sex"` will implicitly join the Patient data type.

TODO: THIS SECTION ISN'T ENTIRELY ACCURATE

```json
[
    {
        "_context_id": 1,
        "_context_value": {
            "patient_id": 1,
            "gender": "Male"
        }
    },
    {
        "_context_id": 2,
        "_context_value": {
            "patient_id": 2,
            "gender": "Female"
        }
    }
]
```

## Data Structures

### Iterators (Rows) and Lists (Columns)
Spark datasets define data as rows containing columns of information. Columns in Spark support hierarchical data structures via nested `StructType`, `ArrayType`, and `MapType` fields.

can also contain rows in the form of list items. 

which are joinable to rows from other datasets. 

A join operation 
TODO: Explain how Expression results can be assigned to a scalar and Spark doesn't support assigning a dataset as a Column value. It's also faster b/c you don't need to group by.

[Anytime value assignment from a many cardinality to a one cardinality requires `collect_list` because it becomes rooted in a single row]
[Cartesean product vs assignment]

### Context Cardinality
When the left-and-right-hand sides of the join share the same cardinality - that is when they both define one row per patient - they're said to have a *one-to-one* relationship. When the right-hand side has many rows per patient, they're said to have a *one-to-many* relationship.

Since Spark supports complex array and struct types, it's possible to convert cardinalities using `explode` or `collect_list` operations. Spark CQL will switch between cardinalities as needed to support certain expressions. However, since these operations come at a cost the preference is to preserve existing cardinalities whenever possible.

## Literal Definitions

CQL literal expressions, like all expressions, are based on context framing which repeats their value for each context element using a *one-to-one* cadinality. For instance, the literal tuple `define "MyLiteral": 42` is instantiated for each patient and assigned to the system attribute `_value`.

```json
[
    {
        "_context_id": 1,
        "_value": 42
    },
    {
        "_context_id": 2,
        "_value": 42
    }
]
```

Literal tuples express complex data structures using a `field=value` format but generally follow the same rules. For instance, the literal tuple `define "LiteralTuple": Tuple { maxSystolic: 140, maxDiastolic: 90 }` is instantiated for each patient and its attributes, `maxSystolic` and `maxDiastolic` make up a struct expression under `_value`.

```json
[
    {
        "_context_id": 1,
        "_value": {
            "maxSystolic": 140,
            "maxDiastolic": 90
        }
    },
    {
        "_context_id": 2,
        "_value": {
            "maxSystolic": 140,
            "maxDiastolic": 90
        }
    }
]
```

##  Retrieve Definitions

Retrieves introduce a *one-to-many* cardinality noting there can be multiple records per patient.

```json
[
    {
        "_context_id": 1,
        "resourceType": "Encounter",
        "id": "001",
        "status" : "completed"
    },
    {
        "_context_id": 1,
        "resourceType": "Encounter",
        "id": "002",
        "status" : "completed"
    },
    {
        "_context_id": 2,
        "resourceType": "Encounter",
        "id": "003",
        "status" : "completed"
    },
    {
        "_context_id": 2,
        "resourceType": "Encounter",
        "id": "004",
        "status" : "completed"
    }
]
```

## Assignment of One-to-Many to One-to-One Members
When a
```json
[
    {
        "_value": [
            {
                "_context_id": 1,
                "resourceType": "Encounter",
                "id": "001",
                "status" : "completed"
            },
            {
                "_context_id": 1,
                "resourceType": "Encounter",
                "id": "002",
                "status" : "completed"
            }
        ]
    },
    {
        "_value": [
            {
                "_context_id": 2,
                "resourceType": "Encounter",
                "id": "003",
                "status" : "completed"
            },
            {
                "_context_id": 2,
                "resourceType": "Encounter",
                "id": "004",
                "status" : "completed"
            }
        ]
    },
]
```