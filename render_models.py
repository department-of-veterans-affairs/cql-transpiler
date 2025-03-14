# Renders all model test files

import os
from jinja2 import Environment, FileSystemLoader
from render_dbt_model import render_dbt_model

def render_models(resources_folder, jinja_subfolder, model_order_file, target_subfolder, skip_failing_files, autoformat):
    # Read the order of model files from the plaintext document
    with open(model_order_file, "r") as f:
        model_files = [line.strip() for line in f]

    # creates the jinja environment
    environment = Environment(loader=FileSystemLoader(resources_folder + jinja_subfolder))
    environment.add_extension('jinja2.ext.do')

    # compile it and render files
    for model_file in model_files:
        try:
            render_dbt_model(environment, resources_folder + jinja_subfolder, resources_folder + target_subfolder, model_file, '.sql', autoformat)
        except Exception as e:
            if skip_failing_files:
                print("failed to render <" + model_file + "> --> skipping")
            else:
                raise e