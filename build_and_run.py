import subprocess
import sys
import os

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
    try:
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
        java_command = (
            f"java -cp {jar_path} gov.va.transpiler.jinja.Transpiler "
            "librarySource=./resources/cql/ "
            "jinjaTarget=./resources/jinja/ "
            "targetLanguage=sparksql "
            "printFunctions=false"
        )
        if not run_command(java_command):
            raise Exception("Transpiler execution failed")

        # Invoke the render_models.py script
        print("Invoking render_models.py...")
        if not run_command("python render_models.py"):
            raise Exception("render_models.py execution failed")

    except Exception as e:
        print(e)

    # Wait for user input before exiting
    input("Press any key to exit...")

if __name__ == "__main__":
    main()
