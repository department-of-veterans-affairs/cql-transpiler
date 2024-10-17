{%- from "library/globals/StandardFunctions.sql" import StandardFunctionsInit %}
{%- from "library/globals/ContextHandlingFunctions.sql" import ContextHandlingFunctionsInit %}
{%- from "library/globals/ListPrintingFunctions.sql" import ListPrintingFunctionsInit %}

{#- Wraps SQL statement in a collect block #}
{%- macro collect(environment, context, toCollect) -%}
    SELECT collect_list(struct(*)) AS {{ environment.printSingleValueColumnName(environment) }} FROM ({{ toCollect }}){%- if context != 'Unfiltered' %} GROUP BY {{ environment.printIDFromContext(environment, context) }}{% endif %}
{%- endmacro %}

{#- Wraps SQL statement in a decollect block #}
{%- macro decollect(environment, context, toDecollect) -%}
    SELECT col.* FROM (explode({{ toDecollect }}))
{%- endmacro %}

{#- Handles collection/decollection of tables #}
{%- macro coerce(environment, originalType, targetType, state, arguments) %}
    {%- if originalType == environment.DataTypeEnum.UNDETERMINED -%}
        /* warning -- cannot coerce object of undetermined type to {{ targetType }} type */ {#- -#}
        {{ arguments['operator'].print(environment, arguments['operator'], state, arguments) }}
    {%- else -%}
        {%- if targetType == environment.DataTypeEnum.ENCAPSULATED and originalType == environment.DataTypeEnum.TABLE -%}
            {{ collect(environment, context, arguments['operator'].print(environment, arguments['operator'], state, arguments)) }}
        {%- elif targetType == environment.DataTypeEnum.TABLE and originalType == environment.DataTypeEnum.ENCAPSULATED -%}
            {{ decollect(environment, context, arguments['operator'].print(environment, arguments['operator'], state, arguments)) }}
        {%- else -%}
            {{ arguments['operator'].print(environment, arguments['operator'], state, arguments) }}
        {%- endif %}
    {%- endif -%}
{%- endmacro %}

{%- macro TypeConversionFunctionsInit(environment) %}
    {#- initialize prerequisites #}
    {%- do StandardFunctionsInit(environment) %}
    {%- do ContextHandlingFunctionsInit(environment) %}
    {%- do ListPrintingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set environment.coerce = coerce %}
{%- endmacro %}
