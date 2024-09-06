{%- macro printMockDBTFunction(name, arguments) -%}
    {{ '{{ ' }}
    {%- if arguments == none or arguments|length == 0 -%}
        {{ name }}()
    {%- elif arguments|length == 1 -%}
        {{ name }}('{{ arguments[0] }}')
    {%- else -%}
        {{ name }}{{ arguments }}
    {%- endif -%}
    {{ ' }}' }}
{%- endmacro %}

{%- macro source() -%}
    {{ printMockDBTFunction('source', varargs) }}
{%- endmacro %}

{%- macro ref() -%}
    {{ printMockDBTFunction('ref', varargs) }}
{%- endmacro %}

{%- macro env_var() -%}
    {{ printMockDBTFunction('env_var', varargs) }}
{%- endmacro %}