# Renders all model test files

import os
from jinja2 import Environment, FileSystemLoader
from render_dbt_model import render_dbt_model

def render_models(resources_folder, jinja_subfolder, intermediate_ast_folders, target_subfolder, skip_failing_files, autoformat):
    # creates the jinja environment
    environment = Environment(loader=FileSystemLoader(resources_folder + jinja_subfolder))
    environment.add_extension('jinja2.ext.do')

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