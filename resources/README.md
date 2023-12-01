To run the transpiled models in a dbt environment, copy the contents of the "transpiler_dbt_models" into the target DBT repository and add them into the relevant model.yml file.

The target DBT repository will need to include implementations of macros specified within resources/jinja/custom_overrides/README.md.