Jinja2 and DBT are very similar languages, but there are some critical differences.

* DBT does not support "from" or "include" statements
** You can use "from" and "include" statements inside of library code, but CANNOT use them inside of model codes.
* You cannot reference a variable from another file in DBT.
* DBT always has all defined macros in context