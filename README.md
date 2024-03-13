# sparkcql
Evaluation of Clinical Quality Language using Spark

To use:

* Add any needed CQL libraries in the test_cql folder
* Specify the CQL to transpile inside the main function of gov.va.transpiler.jinja.Transpiler
* ensure a _macros_sparksql file is present in the jinja_output folder. (An implementation can be found in the transpiler/src/jinja/resources/jinja-support/templates/sparksql folder)
* Run the main function of gov.va.transpiler.jinja.Transpiler
* If an error complains about files already existing in the "jinja_output" folder delete any jinja files present in that folder (exluding the macros file) and re-run the main function
* At this point, jinja files corresponding to the translated CQL file and its dependencies will be present in the jinja_output folder. These jinja files can be used to define other jinja macros and supplied to DBT to build.
* To see the Spark SQL the jinja files resolve to, "python run_jinja.py" in the command line. The script will print the SQL matching each eExpression defined in the original CQL

**Under Development**