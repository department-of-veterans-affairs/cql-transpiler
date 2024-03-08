from jinja2 import Environment, FileSystemLoader
template_dir = "jinja_output"
cql_to_jinja_source = "Retrievals_1.0"
env = Environment(loader = FileSystemLoader(template_dir))
env.trim_blocks = True
env.lstrip_blocks = True
template = env.get_template(cql_to_jinja_source)
print(template.render())
# ".a()" should be replaced with whatever module we actually want to call
# template.module.a()