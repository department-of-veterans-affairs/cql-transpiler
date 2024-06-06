{#
    Environment prerequisites:
        * StandardFunctions.sql
        * ContextHandlingFunctions.sql
#}
{% from "library/globals/ContextHandlingFunctions.sql" import ContextHandlingFunctionsInit %}
{% from "library/globals/ListPrintingFunctions.sql" import ListPrintingFunctionsInit %}

{# Wraps SQL statement in a collect block #}
{%- macro collect(environment, context, toCollect)%}
SELECT collect_list(struct(*)) AS {{ environment.printSingleValueColumnName(environment) }} FROM ({{ toCollect }})
{%-     if context != 'Unfiltered' %}
 GROUP BY {{ environment.printIDFromContext(environment, context) }}
{%-     endif %}
{% endmacro %}

{# Wraps SQL statement in a decollect block #}
{%- macro decollect(environment, context, toDecollect) %}
SELECT col.* FROM (SELECT explode(*) FROM ({{ toDecollect }}))
{%- endmacro %}

{# Wraps SQL statement in an encapsulate block #}
{%- macro encapsulate(environment, context, toEncapsulate) %}
SELECT {{ toEncapsulate }} {{ environment.printSingleValueColumnName(environment) }}
{%- endmacro %}

{# Coerces the first applicable child of this node into the correct type #}
{%- macro coerce(environment, originalType, targetType, state, arguments) %}
{%-     set previousCoercionInstructions = state.coercionInstructions %}
{%-     if targetType == environment.DataTypeEnum.ENCAPSULATED and originalType == environment.DataTypeEnum.SIMPLE %}
{%-         set state.coercionInstructions = {} %}
{{ encapsulate(environment, context, arguments['operator'].print(environment, arguments['operator'], state, arguments)) }}
{%-         set state.coercionInstructions = previousCoercionInstructions %}
{%-     elif targetType == environment.DataTypeEnum.ENCAPSULATED and originalType == environment.DataTypeEnum.TABLE %}
{%-         set state.coercionInstructions = {} %}
{{ collect(environment, context, arguments['operator'].print(environment, arguments['operator'], state, arguments)) }}
{%-         set state.coercionInstructions = previousCoercionInstructions %}
{%-     elif targetType == environment.DataTypeEnum.TABLE and originalType == environment.DataTypeEnum.ENCAPSULATED %}
{%-         set state.coercionInstructions = {} %}
{{ decollect(environment, context, arguments['operator'].print(environment, arguments['operator'], state, arguments)) }}
{%-         set state.coercionInstructions = previousCoercionInstructions %}
{%-     else %}
{{ arguments['operator'].print(environment, arguments['operator'], state, arguments) }}
{%-     endif %}
{%- endmacro %}

{% macro TypeConversionFunctionsInit(environment) -%}
{#      initialize prerequisites #}
{%-     do ContextHandlingFunctionsInit(environment) %}
{%-     do ListPrintingFunctionsInit(environment) %}
{#      initialize member variables #}
{%-     set environment.coerce = coerce %}
{% endmacro %}
