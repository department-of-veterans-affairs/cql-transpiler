import subprocess
import os
from render_models import render_models

def run_command(command):
    process = subprocess.Popen(command, shell=True, text=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    for line in process.stdout:
        print(line, end='')
    for line in process.stderr:
        print(line, end='')
    process.wait()
    if process.returncode != 0:
        print(f"Command failed with return code {process.returncode}")
        return False
    return True

def main():
    # Hardcoded variables to control skipping steps
    skip_java_transpiler = False
    skip_render_models = False

    # Hardcoded variables for the project
    resources_folder = "resources/"
    cql_subfolder = "cql/"
    jinja_subfolder = 'jinja/'
    model_order_file = resources_folder + jinja_subfolder + "model_order.txt"

    if not skip_java_transpiler:
        # Build the project
        print("Building the project...")
        if not run_command("gradlew clean build"):
            raise Exception("Build failed")

        # Verify the existence of the jar file
        jar_path = "transpiler/build/libs/transpiler-1.0-SNAPSHOT.jar"
        if not os.path.exists(jar_path):
            raise Exception(f"Jar file not found: {jar_path}")

        # Run the Transpiler class with the specified arguments
        print("Running the Transpiler...")
        cql_libraries_to_transpile = "CMS104-v12-0-000-QDM-5-6.cql,CMS506v7/CMS506-v7-0-000-QDM-5-6.cql"
        java_command = (
            f"java -cp {jar_path} gov.va.transpiler.jinja.Transpiler "
            f"librarySource=" + resources_folder + cql_subfolder + " "
            f"jinjaTarget=" + resources_folder + jinja_subfolder + " "
            f"modelOrderFilePath=" + model_order_file + " "
            f"targetLanguage=sparksql "
            f"printFunctions=false "
            f"cqlLibrariesToTranspile={cql_libraries_to_transpile}"
        )
        if not run_command(java_command):
            raise Exception("Transpiler execution failed")

    if not skip_render_models:
        # Set variables for render_models
        target_subfolder = "transpiler_dbt_models/"
        skip_failing_files = False
        autoformat = True

        # Invoke the render_models function
        print("Invoking render_models function...")
        render_models(resources_folder, jinja_subfolder, model_order_file, target_subfolder, skip_failing_files, autoformat)

    # Wait for user input before exiting
    input("Press any key to exit...")

if __name__ == "__main__":
    main()
