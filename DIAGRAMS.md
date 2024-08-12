Reading this file requires [mermaid](https://github.com/mermaid-js/mermaid).

# Main Overview
```mermaid
flowchart TD

A[CQL Text File] -->|Java Transpiler| B[Jinja/DBT text files]
B -->C{Compilation Environment}
C -->|test_models.py| D[SQL Files With Placeholders]
C -->|Databricks Environment - DBT Compile| E[Valid SQL Files]
E -->|Running against servers| F[MSSQL Measure Results]
```

# Measure Logic Transformation Overview

```mermaid
flowchart TB
    subgraph fs [File System]
        subgraph cql["CQL Libraries (as text)"]
            PrereqCQL["Prerequisite CQL Libraries"]
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
                subgraph externalDependencies["External Dependencies"]
                    subgraph cqlFramework["CQL Framework Libraries"]
                        libraryWorkA["CQL (as text) (in memory)"]
                        libraryWorkB["CQL (as language model (ELM))"]
                        libraryWorkC["CQL (as abstract syntax tree (AST))"]
                        libraryWorkA --> libraryWorkB --> libraryWorkC
                    end
                    reference["See: https://github.com/cqframework/cqf"]
                    reference .- cqlFramework
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
    subgraph python["Local Python Environment"]
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

# Unit Testing Java Code

Unit tests still need to be created for Java.

# Integration Testing Jinja Code

Java code does not need to be integration tested.

# Unit Testing Jinja Code
Run test_operators.py and look at outputs in resources/test_target/operators.

# Integration Testing Jinja Code
Run test_models.py and look at outputs in resources/test_target/models