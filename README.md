# cql-transpiler

The CQL Transpiler is a tool for translating [Clinical Quality Language (CQL)](https://cql.hl7.org/) to its SQL equivalence. The transpiler itself doesn't actually produce SQL but instead translates each ELM operator into [DBT](https://getdbt.com/)/[Jinja](https://jinja.palletsprojects.com/en/3.1.x/) *macro operator* equivalents. These *macro operators* are translated to a given SQL dialect through a separate DBT *transpiler target* package imported into the implementor's solution. Spark SQL is the first planned *transpiler target* but the development and use of other targets is possible.

This project is still very much in its infancy but is hoped to be used for VA's 2024 Joint Commission submission.

## Overview

The transpiler workflow is as follows

1. CQL text files are loaded into memory
2. CQL text files are converted into CQL Abstract Syntax Trees (ASTs) in memory
3. CQL ASTs are converted into a "generic" AST that can be used to output code in any query language.
4. The generic ASTs are rendered in text parsable by jinja
5. The generic ASTs are rendered as DBT models.

## Using the Transpiler

* Replace the contents of the resources/cql folder with the CQL libraries to be transpiled.
* Run the main function of gov.va.transpiler.jinja.Transpiler
 * The java portion of the transpiler will create intermediate ASTs as jinja files for the original CQL file and its dependencies in the "resources/jinja/generated_intermediate/" folder.
 * The java portion of the transpiler will create model files as jinja files for each expression in the original CQL file in the "resources/jinja/generated_models/" folder.
* Run the "render_models.py" script.
 * The python/jinja portion of the transpiler will create model files as dbt files for each expression in the original CQL file in the "resources/transpiler_dbt_models/" folder.
* Copy the dbt files into your dbt repository.
* Implement any implementation/database-specific macro files within your dbt repository.
* Execute the normal workflow for your dbt repository.


### Design - CQL-to-Jinja

The "transpiler" module and specifically the main function "Transpiler" class are the entry point into the java portion of the transpiler.

### Design - Jinja-to-DBT

The "render_models.py" script is the entry point into the jinja portion of the transpiler.

#### Design patterns:

Jinja macros files should implement transpilation logic using an object-oriented programming paradigm.
Jinja does not support object-oriented programming natively, but it can be hacked to allow it through the clever use of namespaces.
To create an an object's "constructor", create a macro function that sets certain variables on a namespace.
To create an object that "inherits" from another object, in its constructor, run the parent constructor on the namespace.
To create a function that returns one or more values, create a macro that accepts a "carrier" namespace and sets its return values inside that namespace without printing anything.

#### Prerequisites

##### State

The jinja AST rendering process does not assume any particular behavior from the State class. The State class exists for the macro file's use.

##### Operator

The Operator class must support the following members:

    + operator - namespace (extends from Operator)
    + children - list of operatorValues

Classes extending from the operator class should support other data passed on from the CQL version of the operators.

##### OperatorHandler

The OperatorHandler class must support the following members:

print(state, operatorValues) - void macro
    + state - namespace (nullable)
    + operatorValues - dictionary

The operatorValues dictionary should be treated as essentially a class, that always contains at least the following members:
    + operator - namespace (extends from Operator)
    + children - list of operatorValues

### Design - Jinja-to-SparkSQL

The macros file that targets SparkSQL implements its behavior using the following classes and standards.

#### State

State - namespace
    + context - String (scoped)
    + functionArguments - Dictionary<String, Dictionary<String, ?>> (scoped)
    + aliasContext - String

note: "scoped" variables should be stored and reset before descending into any branch of the AST where they're modified.
To do so, use the following pattern:

{%- set previousVariableName = state.variableName %}
{%- set state.variableName = newValue %}
{{ function(state) }}
{%- set state.variableName = previousVariableName %}

#### DataType

DataType is an enum.

Values:
    SIMPLE = "simple"
    ENCAPSULATED = "encapsulated"
    TABLE = "table"
    INHERITED = "inherited"
    STATEMENT = "statement"
    UNKNOWN = "unknown"

#### Operator

Operator - namespace
    + getDataType(carrier, arguments) - macro (returns DataType)
        + carrier - namespace
            + return - string
        + arguments - dictionary extending OperatorValues
    + print(state, arguments)
        + state - State
        + arguments - dictionary extending OperatorValues

    Potential data types:

#### OperatorHandler

print(state, operatorValues) - macro
    + state - namespace (nullable)
    + operatorValues - dictionary
