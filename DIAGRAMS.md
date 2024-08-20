Reading this file requires [mermaid](https://github.com/mermaid-js/mermaid).

# Simplified Overview
```mermaid
flowchart TD

A[CQL Text File] -->|Java Transpiler| B[Jinja/DBT text files]
B -->C{Compilation Environment}
C -->|test_models.py| D[SQL Files With Placeholders]
C -->|Databricks Environment - DBT Compile| E[Valid SQL Files]
E -->|Running against servers| F[MSSQL Measure Results]
```
# Main Overview
```mermaid
flowchart LR

subgraph java["Java"]
    a(["Parse text"]) --> aa(["Create Logical Tree"])
    aa --> aaa(["Convert to Query Language Semantics"])
end
A["CQL"] --> java
java --> B["Query Logical Tree"]
subgraph dbt["Jinja/DBT"]
    i(["Parse Tree"]) --> ii(["Adapt for Dialect"])
    ii --> iii(["Adapt for Database"])
end
B --> dbt
dbt --> C["SQL"]
```

# Measure Logic Transformation Overview

```mermaid

flowchart TD
    subgraph fs [File System]
        subgraph cql["CQL Libraries (as text)"]
            PrereqCQL["`Prerequisite CQL Libraries`"]
            CQLToTranslate["CQL Libraries to Translate"]
        end
        JinjaText["Intermediate AST (as text)"]
        cqlInEQM["Intermediate AST (as text) (inside eqm repository)"]
        JinjaText-->|"Databricks DBT"|cqlInEQM
        SQL["Generated SQL File"]
    end
    subgraph jvm [JVM]
        subgraph transpiler ["transpiler module"]
            subgraph sparksqlcqf ["sparksql-cqf module"]
                subgraph externalDependencies["External Dependencies (See: https://github.com/cqframework/cqf)"]
                    subgraph cqlFramework["CQL Framework Libraries"]
                        libraryWorkA["CQL (as text) (in memory)"]
                        libraryWorkB["CQL (as language model (ELM))"]
                        libraryWorkC["CQL (as abstract syntax tree (AST))"]
                        libraryWorkA --> libraryWorkB --> libraryWorkC
                    end
                end
            end
            CQLAsTextTranspiler["CQL (as text) (in memory)"]
            CQLAsTextTranspiler -->|"CQFCompiler.java"| libraryWorkA
            IntermediateASTNodes["Intermediate AST (as TranspilerNode tree)"]
            libraryWorkC -->|"Converter.java"| IntermediateASTNodes
            IntermediateASTSegments["Intermediate AST (as Segment tree)"]
            IntermediateASTNodes -->|"TranspilerNode toSegment"| IntermediateASTSegments
            IntermediateASTSegments -->|"SegmentPrinter.java"| JinjaText
        end
    end
    cql -->|"Transpiler.java"| CQLAsTextTranspiler
    subgraph python["Python Environment"]
        subgraph jinja["jinja environment"]
            IntermediateASTAsNamespaceWithBlank["Intermediate AST (as jinja namespace) + blank execution environment namespace"]
            IntermediateASTAsNamespaceWithOperators["Intermediate AST (as jinja namespace) + execution environment with operators"]
            IntermediateASTAsNamespaceWithBlank -->|"Init.sql"|IntermediateASTAsNamespaceWithOperators
            IntermediateASTAsNamespaceWithOperators -->|"SQL model file"| SQL
        end
        subgraph dbt["dbt environment"]
            IntermediateASTAsDBTNamespaceWithBlank["Intermediate AST (as dbt namespace) + blank execution environment namespace"]
            IntermediateASTAsDBTNamespaceWithOperators["Intermediate AST (as jinja namespace) + execution environment with operators"]
            IntermediateASTAsDBTNamespaceWithOverridenOperators["Intermediate AST (as jinja namespace) + execution environment with overriden operators"]
            IntermediateASTAsDBTNamespaceWithBlank -->|"Init.sql"|IntermediateASTAsDBTNamespaceWithOperators
            IntermediateASTAsDBTNamespaceWithOperators -->|"InitCustomOverrides.sql"|IntermediateASTAsDBTNamespaceWithOverridenOperators
            IntermediateASTAsDBTNamespaceWithOverridenOperators -->|"SQL model file"| SQL
        end
        JinjaText -->|"test_models.py"| IntermediateASTAsNamespaceWithBlank
        cqlInEQM -->|"dbt compile"| IntermediateASTAsDBTNamespaceWithBlank
    end
```

# Sequence Diagram

```mermaid

sequenceDiagram
    activate File System
    File System ->> File System: Add CQL Dependencies
    File System ->> File System: Add CQL Library to Translate
    File System ->> File System: Set CQL Library to Translate inside Transpiler.java
    File System ->> JVM: Run Transpiler.java
    activate JVM
    File System ->> JVM: Load CQL files from file system
    deactivate File System
    JVM ->> JVM: convert CQL files to CQL AST
    JVM ->> JVM: convert CQL AST to intermediate AST
    JVM ->> File System: Render intermediate AST as jinja/dbt files
    deactivate JVM
    activate File System
    alt running jinja locally
    File System ->> Python Environment: run test_models.py
    activate Python Environment
    Python Environment ->> Jinja Environment: create jinja environment
    activate Jinja Environment
    File System ->> Jinja Environment: load intermediate AST
    File System ->> Jinja Environment: load operator implementations
    File System ->> Jinja Environment: load model files
    Jinja Environment ->> Python Environment: render model files
    deactivate Jinja Environment
    Python Environment ->> File System: generate SQL files
    deactivate Python Environment
    else running dbt against databricks environment
    File System ->> File System: Copy generated files to eqm repository
    File System ->> File System: Copy jinja/dbt library files to eqm repository
    File System ->> Databricks Environment: run dbt compile
    activate Databricks Environment
    File System ->> Databricks Environment: load intermediate AST
    File System ->> Databricks Environment: load operator implementations
    File System ->> Databricks Environment: load operator overrides
    File System ->> Databricks Environment: load model files
    Databricks Environment ->> File System: generate SQL files
    File System ->> SQL Database: Run SQL files
    activate SQL Database
    SQL Database ->> SQL Database: get measure results
    SQL Database ->> File System: store measure results
    deactivate SQL Database
    deactivate Databricks Environment
    deactivate File System
    end
```

# Unit Testing Java Code

Unit tests still need to be created for Java.

# Integration Testing Jinja Code

Java code does not need to be integration tested.

# Unit Testing Jinja Code
Run test_operators.py and look at outputs in resources/test_target/operators.

# Integration Testing Jinja Code
Run test_models.py and look at outputs in resources/test_target/models