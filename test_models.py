# Renders all model test files

import os
from jinja2 import Environment, FileSystemLoader
from render_jinja_file import render_jinja_file
        
# creates the jinja environment
resources_folder = "resources/"
jinja_subfolder = 'jinja/'
test_subfolder = 'test/'
model_subfolder = 'models/'
target_subfolder = "test_target/"
environment = Environment(loader = FileSystemLoader(resources_folder + jinja_subfolder))
environment.add_extension('jinja2.ext.do')

# compiles and renders a single, specific model
render_jinja_file(environment, test_subfolder + model_subfolder, resources_folder + target_subfolder + model_subfolder, 'TJCOverallNon_Elective_Inpatient_Encounter.sql', '.sql')

# for every file inside the models directory, compile it and render it
# model_test_file_names = os.listdir(resources_folder + jinja_subfolder + test_subfolder + model_subfolder)
# for model_test_file_name in model_test_file_names:
#     render_jinja_file(environment, test_subfolder + model_subfolder, resources_folder + target_subfolder + model_subfolder, model_test_file_name, '.sql')
    
