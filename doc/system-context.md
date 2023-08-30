# System Design

## Use Cases

- **UC1 - Configure Environment**
- **UC2 - Calculate Measures**

## Context

- **FHIR API**: 
- **Job Orchestration**: 
- **SparkCQL Engine**: 
- **Adapters**: 
- **Data Lake**: 
- **Cache Store**: 

### Dependency Graph

```mermaid
graph TB
    api[FHIR API]
    job[Job Orchestration]

    sparkcql[Spark CQL]

    service_providers[Adapters]
    lake[(Data Lake)]
    dss[(DS Cache)]

    client((Client))
    
    api -.-> job
    job -.-> sparkcql
    sparkcql -.-> 
    service_providers -.-> lake
    service_providers -.-> dss

    client -.-> api
    client -.self-hosting..-> sparkcql
```