To run the transpiled models in a dbt environment, copy the contents of the "jinja" folder into a macro source folder inside the target DBT repository. Then, in that repository ONLY, delete the "jinja_only" folder.

Afterwards, copy the "transpiler_dbt_models" folder into a model source folder inside the target DBT repository.