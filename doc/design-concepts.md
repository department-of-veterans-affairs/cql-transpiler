# CQL Service Component

## Workflow
- Compile
- Generate Plans
    - Collect retrieves
    - Optimize plans
- Bulk Retrieve
    - Acquire data
	- Apply retrieval filters
	- Group by Context (ContextID, List of RetrieveOps: List )
	- Join by Context
- MapPartitions 
- Evaluate
- Output

## Model Transitions

CQL Authoring to Evaluation...

```mermaid
stateDiagram-v2
    direction TB

    state "<center>Clinical Quality<br>Language (CQL)</center>" as cql
    state "<center>Expression<br>Logical Model (ELM)</center>" as elm
    state "Retrieval Plan" as retrieval_plan {
        direction LR
        state "Data Requirements" as data_requirements
        state "Logical Plan" as logical_plan
        state "Constraints" as logical_plan
        state "Elimination" as logical_plan
        state "Physical Plan" as physical_plan
        state "Determinism" as physical_plan
        state "Cache Misses" as physical_plan
        [*] --> data_requirements
        data_requirements --> logical_plan
        logical_plan --> physical_plan
        physical_plan --> [*]
    }

    state "Bundled Results" as retrieval_results {
        direction LR
        state "Cache" as cache
        state "Bundle" as bundle
        [*] --> cache: Update
        cache --> bundle: Collect
        bundle --> [*]
    }
    state "Evaluation Results" as evaluation_results

    [*] --> cql: Author
    cql --> elm: Compile
    elm --> retrieval_plan: Plan
    retrieval_plan --> retrieval_results: Retrieve
    retrieval_results --> evaluation_results: Evaluate
    evaluation_results --> [*]

```

## Class Design
```mermaid
classDiagram
    class Duck {
        <<interface>>
        +String beakColor
        +swim()
        +quack()
    }
    class Fish {
        -int sizeInFeet
        -canEat()
    }
    class Zebra {
        +bool is_wild
        +run()
    }
```

## Use Case Sequencing
### UC2.1 - Configuration

### UC2.1.1 - Configure Deployment

### UC2.1.2 - Configure Session
```mermaid
sequenceDiagram
    Client ->> Session: build()
    Client ->> SessionBuilder: withConfig(...)
    Client ->> SessionBuilder: create()
    SessionBuilder ->> Service: configure
    SessionBuilder ->> Session: new(...)
    SessionBuilder -->> Client: return session
```

### UC2.2 - Process Measures

```mermaid
sequenceDiagram
    Client ->> Session: build()
    Client ->> SessionBuilder: withConfig(...)
    Client ->> SessionBuilder: create()
    SessionBuilder ->> Session: new
```

TODO
- Consider UC correlation
- Incremental (subpopulation) calculations only performed during bundling operations. Too many joins and overhead for doing this for all retrieves. SparkCQL is optimized for full population analysis.