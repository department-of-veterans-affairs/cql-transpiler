# renders a specific file in jinja
def render_jinja_file(environment, path_to_file_folder_from_jinja_root, path_to_target_folder, file_name, target_file_extension):
    template = environment.get_template(path_to_file_folder_from_jinja_root + file_name)
    rendered = template.render()
    with open(path_to_target_folder + file_name.rsplit('.', 1)[0] + target_file_extension, 'w') as file:
        file.write(rendered)