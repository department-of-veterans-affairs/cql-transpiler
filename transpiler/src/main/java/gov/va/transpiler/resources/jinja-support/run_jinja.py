from jinja2 import Environment, FileSystemLoader
template_dir = "tempjinja"
env = Environment(loader=FileSystemLoader(template_dir))
cql_to_jinja_source = "Retrievals_1_0"
template = env.get_template(cql_to_jinja_source)
# ".a()" should be replaced with whatever module we actually want to call
# template.module.a()