{%- macro RequiresContextEnumInit(environment) %}
    {%- set RequiresContextEnum = namespace() %}
    {%- set RequiresContextEnum.INHERITED_OR_FALSE = 'requirescontext_inheritedOrFalse'%}
    {%- set RequiresContextEnum.TRUE = 'requirescontext_true'%}
    {%- set RequiresContextEnum.FALSE = 'requirescontext_false'%}
    {%- set environment.RequiresContextEnum = RequiresContextEnum %}
{%- endmacro %}
