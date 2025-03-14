import sqlparse
from jinja2 import Environment
import os

# renders an intermediate dbt file into a dbt model
def render_dbt_model(environment: Environment, jinja_folder: str, target_folder: str, model_file: str, extension: str, autoformat: bool):
    # Isolate the model file name
    model_file_name = os.path.basename(model_file)
    
    # Load the jinja template
    template = environment.get_template(model_file)

    # Render the template
    rendered_content = template.render()

    # Optionally autoformat the rendered content
    if autoformat:
        rendered_content = sqlparse.format(rendered_content, reindent=True)

    # Define the output file path
    output_file_path = os.path.join(target_folder, model_file_name.replace('.j2', extension))

    # Ensure the target directory exists
    output_dir = os.path.dirname(output_file_path)
    os.makedirs(output_dir, exist_ok=True)

    # Write the rendered content to the output file
    with open(output_file_path, 'w') as output_file:
        try:
            output_file.write(rendered_content)
        except Exception as e:
            raise e