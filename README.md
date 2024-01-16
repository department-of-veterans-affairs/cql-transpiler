# cql-transpiler

The CQL Transpiler is a tool for translating [Clinical Quality Language (CQL)](https://cql.hl7.org/) to its SQL equivalence. The transpiler itself doesn't actually produce SQL but instead translates each ELM operator into [DBT](https://getdbt.com/)/[Jinja](https://jinja.palletsprojects.com/en/3.1.x/) *macro operator* equivalents. These *macro operators* are translated to a given SQL dialect through a separate DBT *transpiler target* package imported into the implementor's solution. Spark SQL is the first planned *transpiler target* but the development and use of other targets is possible.

This project is still very much in its infancy but is hoped to be used for VA's 2024 Joint Commission submission.
