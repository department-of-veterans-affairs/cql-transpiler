{#
    Loads operators into environment
#}
{% from "library/operators/AddStaticVariable.sql" import AddStaticVariableInit %}
{% from "library/operators/AfterStaticVariable.sql" import AfterStaticVariableInit %}
{% from "library/operators/AliasedQuerySourceStaticVariable.sql" import AliasedQuerySourceStaticVariableInit %}
{% from "library/operators/AndStaticVariable.sql" import AndStaticVariableInit %}
{% from "library/operators/AsStaticVariable.sql" import AsStaticVariableInit %}
{% from "library/operators/BeforeStaticVariable.sql" import BeforeStaticVariableInit %}
{% from "library/operators/ByExpressionStaticVariable.sql" import ByExpressionStaticVariableInit %}
{% from "library/operators/CalculateAgeAtStaticVariable.sql" import CalculateAgeAtStaticVariableInit %}
{% from "library/operators/CoalesceStaticVariable.sql" import CoalesceStaticVariableInit %}
{% from "library/operators/ConcatenateStaticVariable.sql" import ConcatenateStaticVariableInit %}
{% from "library/operators/CountStaticVariable.sql" import CountStaticVariableInit %}
{% from "library/operators/DateFromStaticVariable.sql" import DateFromStaticVariableInit %}
{% from "library/operators/DateTimeStaticVariable.sql" import DateTimeStaticVariableInit %}
{% from "library/operators/DifferenceBetweenStaticVariable.sql" import DifferenceBetweenStaticVariableInit %}
{% from "library/operators/DivideStaticVariable.sql" import DivideStaticVariableInit %}
{% from "library/operators/EndStaticVariable.sql" import EndStaticVariableInit %}
{% from "library/operators/EqualStaticVariable.sql" import EqualStaticVariableInit %}
{% from "library/operators/ExistsStaticVariable.sql" import ExistsStaticVariableInit %}
{% from "library/operators/ExpressionDefStaticVariable.sql" import ExpressionDefStaticVariableInit %}

{% from "library/operators/RetrieveStaticVariable.sql" import RetrieveStaticVariableInit %}
{% from "library/operators/SingletonFromStaticVariable.sql" import SingletonFromStaticVariableInit %}

{% macro init(environment) %}
{%-     do AddStaticVariableInit(environment) %}
{%-     do AfterStaticVariableInit(environment) %}
{%-     do AliasedQuerySourceStaticVariableInit(environment) %}
{%-     do AndStaticVariableInit(environment) %}
{%-     do AsStaticVariableInit(environment) %}
{%-     do BeforeStaticVariableInit(environment) %}
{%-     do ByExpressionStaticVariableInit(environment) %}
{%-     do CalculateAgeAtStaticVariableInit(environment) %}
{%-     do CoalesceStaticVariableInit(environment) %}
{%-     do ConcatenateStaticVariableInit(environment) %}
{%-     do CountStaticVariableInit(environment) %}
{%-     do DateFromStaticVariableInit(environment) %}
{%-     do DateTimeStaticVariableInit(environment) %}
{%-     do DifferenceBetweenStaticVariableInit(environment) %}
{%-     do DivideStaticVariableInit(environment) %}
{%-     do EndStaticVariableInit(environment) %}
{%-     do EqualStaticVariableInit(environment) %}
{%-     do ExistsStaticVariableInit(environment) %}
{%-     do ExpressionDefStaticVariableInit(environment) %}

{%-     do RetrieveStaticVariableInit(environment) %}
{%-     do SingletonFromStaticVariableInit(environment) %}
{%- endmacro %}