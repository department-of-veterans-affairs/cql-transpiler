# Renders all model test files

import os
from jinja2 import Environment, FileSystemLoader
from render_dbt_model import render_dbt_model

# creates the jinja environment
resources_folder = "resources/"
jinja_subfolder = 'jinja/'
intermediate_ast_folders = ['generated_models/MATGlobalCommonFunctions_7__0__000/', 'generated_models/TJCOverall_7__1__000/', 'generated_models/DischargedonAntithromboticTherapy_12__0__000/', 'generated_models/MATGlobalCommonFunctionsQDM_8__0__000/', 'generated_models/SafeUseofOpioidsConcurrentPrescribing_7__0__000/']
target_subfolder = "transpiler_dbt_models/"
environment = Environment(loader = FileSystemLoader(resources_folder + jinja_subfolder))
environment.add_extension('jinja2.ext.do')
skip_failing_files = False
autoformat = True

# compiles and renders a single, specific model
# render_jinja_file(environment, test_subfolder + model_subfolder, resources_folder + target_subfolder + model_subfolder, 'TJCOverallNon_Elective_Inpatient_Encounter.sql', '.sql')

# compile it and render files
for folder in intermediate_ast_folders:
    model_test_file_names = os.listdir(resources_folder + jinja_subfolder + folder)
    for model_test_file_name in model_test_file_names:
        try:
            render_dbt_model(environment, folder, resources_folder + target_subfolder, model_test_file_name, '.sql', autoformat)
        except Exception as e:
            if skip_failing_files:
                print("failed to render <" + model_test_file_name + "> --> skipping")
            else:
                raise e