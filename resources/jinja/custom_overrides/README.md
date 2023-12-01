The "Custom overrides" folder enables printing operators that require implementation and database-specific logic by genarating dbt model files that reference unimplemented macros. Unimplemented macros should be implemented by the dbt repository where the generated dbt models will be placed inside to be run against a databricks environment.

Macros that need to be implemented in the target dbt repository include:
* transpilerRetrieve
* transpilerValuesetCodes
* transpilerUnionColumns