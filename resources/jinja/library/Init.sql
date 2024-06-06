{#
    Loads operators into environment
#}
{% from "library/operators/ExpressionDefStaticVariable.sql" import ExpressionDefStaticVariableInit %}
{% from "library/operators/SingletonFromStaticVariable.sql" import SingletonFromStaticVariableInit %}
{% from "library/operators/RetrieveStaticVariable.sql" import RetrieveStaticVariableInit %}

{% macro init(environment) %}
{%-     do ExpressionDefStaticVariableInit(environment) %}
{%-     do SingletonFromStaticVariableInit(environment) %}
{%-     do RetrieveStaticVariableInit(environment) %}
{%- endmacro %}