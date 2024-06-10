{#
    Loads operators into environment
#}
{% from "library/operators/AddStaticVariable.sql" import AddStaticVariableInit %}
{% from "library/operators/ExpressionDefStaticVariable.sql" import ExpressionDefStaticVariableInit %}
{% from "library/operators/RetrieveStaticVariable.sql" import RetrieveStaticVariableInit %}
{% from "library/operators/SingletonFromStaticVariable.sql" import SingletonFromStaticVariableInit %}

{% macro init(environment) %}
{%-     do AddDefStaticVariableInit(environment) %}
{%-     do AsDefStaticVariableInit(environment) %}
{%-     do ExpressionDefStaticVariableInit(environment) %}
{%-     do RetrieveStaticVariableInit(environment) %}
{%-     do SingletonFromStaticVariableInit(environment) %}
{%- endmacro %}