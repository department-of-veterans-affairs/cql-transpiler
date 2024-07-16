# Renders all operator test files

import os
from jinja2 import Environment, FileSystemLoader
from render_jinja_file import render_jinja_file
        
# creates the jinja environment
resources_folder = "resources/"
jinja_subfolder = 'jinja/'
test_subfolder = 'test/'
operator_subfolder = 'operators/'
target_subfolder = "test_target/"
environment = Environment(loader = FileSystemLoader(resources_folder + jinja_subfolder))
environment.add_extension('jinja2.ext.do')

# for every file inside the operators directory, compile it and render it
operator_test_file_names = os.listdir(resources_folder + jinja_subfolder + test_subfolder + operator_subfolder)
for operator_test_file_name in operator_test_file_names:
    render_jinja_file(environment, test_subfolder + operator_subfolder, resources_folder + target_subfolder + operator_subfolder, operator_test_file_name, '.txt')