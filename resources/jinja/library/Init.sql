{#- Entry point -#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}

{#- Sets up logger #}
{%- from "library/globals/LoggerFunctions.sql" import LoggerFunctionsInit %}

{#- Loads operators into environment #}
{%- from "library/operators/AddStaticVariable.sql" import AddStaticVariableInit %}
{%- from "library/operators/AfterStaticVariable.sql" import AfterStaticVariableInit %}
{%- from "library/operators/AliasedQuerySourceStaticVariable.sql" import AliasedQuerySourceStaticVariableInit %}
{%- from "library/operators/AliasRefStaticVariable.sql" import AliasRefStaticVariableInit %}
{%- from "library/operators/AndStaticVariable.sql" import AndStaticVariableInit %}
{%- from "library/operators/AsStaticVariable.sql" import AsStaticVariableInit %}
{%- from "library/operators/BeforeStaticVariable.sql" import BeforeStaticVariableInit %}
{%- from "library/operators/ByExpressionStaticVariable.sql" import ByExpressionStaticVariableInit %}
{%- from "library/operators/CalculateAgeAtStaticVariable.sql" import CalculateAgeAtStaticVariableInit %}
{%- from "library/operators/CoalesceStaticVariable.sql" import CoalesceStaticVariableInit %}
{%- from "library/operators/ConcatenateStaticVariable.sql" import ConcatenateStaticVariableInit %}
{%- from "library/operators/CountStaticVariable.sql" import CountStaticVariableInit %}
{%- from "library/operators/DateFromStaticVariable.sql" import DateFromStaticVariableInit %}
{%- from "library/operators/DateTimeComponentFromStaticVariable.sql" import DateTimeComponentFromStaticVariableInit %}
{%- from "library/operators/DateTimeStaticVariable.sql" import DateTimeStaticVariableInit %}
{%- from "library/operators/DifferenceBetweenStaticVariable.sql" import DifferenceBetweenStaticVariableInit %}
{%- from "library/operators/DivideStaticVariable.sql" import DivideStaticVariableInit %}
{%- from "library/operators/EndStaticVariable.sql" import EndStaticVariableInit %}
{%- from "library/operators/EqualStaticVariable.sql" import EqualStaticVariableInit %}
{%- from "library/operators/ExistsStaticVariable.sql" import ExistsStaticVariableInit %}
{%- from "library/operators/ExpressionDefStaticVariable.sql" import ExpressionDefStaticVariableInit %}
{%- from "library/operators/ExpressionRefStaticVariable.sql" import ExpressionRefStaticVariableInit %}
{%- from "library/operators/FirstStaticVariable.sql" import FirstStaticVariableInit %}
{%- from "library/operators/FunctionDefStaticVariable.sql" import FunctionDefStaticVariableInit %}
{%- from "library/operators/FunctionRefStaticVariable.sql" import FunctionRefStaticVariableInit %}
{%- from "library/operators/GreaterOrEqualStaticVariable.sql" import GreaterOrEqualStaticVariableInit %}
{%- from "library/operators/GreaterStaticVariable.sql" import GreaterStaticVariableInit %}
{%- from "library/operators/IdentifierRefStaticVariable.sql" import IdentifierRefStaticVariableInit %}
{%- from "library/operators/IfStaticVariable.sql" import IfStaticVariableInit %}
{%- from "library/operators/InIntervalStaticVariable.sql" import InIntervalStaticVariableInit %}
{%- from "library/operators/InStaticVariable.sql" import InStaticVariableInit %}
{%- from "library/operators/IntervalStaticVariable.sql" import IntervalStaticVariableInit %}
{%- from "library/operators/IntervalTypeSpecifierStaticVariable.sql" import IntervalTypeSpecifierStaticVariableInit %}
{%- from "library/operators/InValueSetStaticVariable.sql" import InValueSetStaticVariableInit %}
{%- from "library/operators/IsNullStaticVariable.sql" import IsNullStaticVariableInit %}
{%- from "library/operators/LastStaticVariable.sql" import LastStaticVariableInit %}
{%- from "library/operators/LessOrEqualStaticVariable.sql" import LessOrEqualStaticVariableInit %}
{%- from "library/operators/LessStaticVariable.sql" import LessStaticVariableInit %}
{%- from "library/operators/LetClauseStaticVariable.sql" import LetClauseStaticVariableInit %}
{%- from "library/operators/ListStaticVariable.sql" import ListStaticVariableInit %}
{%- from "library/operators/ListTypeSpecifierStaticVariable.sql" import ListTypeSpecifierStaticVariableInit %}
{%- from "library/operators/LiteralStaticVariable.sql" import LiteralStaticVariableInit %}
{%- from "library/operators/MaxValueStaticVariable.sql" import MaxValueStaticVariableInit %}
{%- from "library/operators/MinValueStaticVariable.sql" import MinValueStaticVariableInit %}
{%- from "library/operators/MultiplyStaticVariable.sql" import MultiplyStaticVariableInit %}
{%- from "library/operators/NamedTypeSpecifierStaticVariable.sql" import NamedTypeSpecifierStaticVariableInit %}
{%- from "library/operators/NegateStaticVariable.sql" import NegateStaticVariableInit %}
{%- from "library/operators/NotStaticVariable.sql" import NotStaticVariableInit %}
{%- from "library/operators/NullStaticVariable.sql" import NullStaticVariableInit %}
{%- from "library/operators/OperandDefStaticVariable.sql" import OperandDefStaticVariableInit %}
{%- from "library/operators/OperandRefStaticVariable.sql" import OperandRefStaticVariableInit %}
{%- from "library/operators/OrStaticVariable.sql" import OrStaticVariableInit %}
{%- from "library/operators/ParameterRefStaticVariable.sql" import ParameterRefStaticVariableInit %}
{%- from "library/operators/PropertyStaticVariable.sql" import PropertyStaticVariableInit %}
{%- from "library/operators/QuantityStaticVariable.sql" import QuantityStaticVariableInit %}
{%- from "library/operators/QueryLetRefStaticVariable.sql" import QueryLetRefStaticVariableInit %}
{%- from "library/operators/QueryStaticVariable.sql" import QueryStaticVariableInit %}
{%- from "library/operators/RetrieveStaticVariable.sql" import RetrieveStaticVariableInit %}
{%- from "library/operators/ReturnClauseStaticVariable.sql" import ReturnClauseStaticVariableInit %}
{%- from "library/operators/SingletonFromStaticVariable.sql" import SingletonFromStaticVariableInit %}
{%- from "library/operators/SortClauseStaticVariable.sql" import SortClauseStaticVariableInit %}
{%- from "library/operators/StartStaticVariable.sql" import StartStaticVariableInit %}
{%- from "library/operators/SubtractStaticVariable.sql" import SubtractStaticVariableInit %}
{%- from "library/operators/TimezoneOffsetFromStaticVariable.sql" import TimezoneOffsetFromStaticVariableInit %}
{%- from "library/operators/ToDateStaticVariable.sql" import ToDateStaticVariableInit %}
{%- from "library/operators/ToDecimalStaticVariable.sql" import ToDecimalStaticVariableInit %}
{%- from "library/operators/TupleElementStaticVariable.sql" import TupleElementStaticVariableInit %}
{%- from "library/operators/TupleStaticVariable.sql" import TupleStaticVariableInit %}
{%- from "library/operators/TypeSpecifierStaticVariable.sql" import TypeSpecifierStaticVariableInit %}
{%- from "library/operators/UnionStaticVariable.sql" import UnionStaticVariableInit %}
{%- from "library/operators/UnsupportedStaticVariable.sql" import UnsupportedStaticVariableInit %}
{%- from "library/operators/ValueSetDefStaticVariable.sql" import ValueSetDefStaticVariableInit %}
{%- from "library/operators/ValueSetRefStaticVariable.sql" import ValueSetRefStaticVariableInit %}
{%- from "library/operators/WithStaticVariable.sql" import WithStaticVariableInit %}

{%- macro initEnvironment(environment) %}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do LoggerFunctionsInit(environment) %}

    {%- do AddStaticVariableInit(environment) %}
    {%- do AfterStaticVariableInit(environment) %}
    {%- do AliasedQuerySourceStaticVariableInit(environment) %}
    {%- do AliasRefStaticVariableInit(environment) %}
    {%- do AndStaticVariableInit(environment) %}
    {%- do AsStaticVariableInit(environment) %}
    {%- do BeforeStaticVariableInit(environment) %}
    {%- do ByExpressionStaticVariableInit(environment) %}
    {%- do CalculateAgeAtStaticVariableInit(environment) %}
    {%- do CoalesceStaticVariableInit(environment) %}
    {%- do ConcatenateStaticVariableInit(environment) %}
    {%- do CountStaticVariableInit(environment) %}
    {%- do DateFromStaticVariableInit(environment) %}
    {%- do DateTimeComponentFromStaticVariableInit(environment) %}
    {%- do DateTimeStaticVariableInit(environment) %}
    {%- do DifferenceBetweenStaticVariableInit(environment) %}
    {%- do DivideStaticVariableInit(environment) %}
    {%- do EndStaticVariableInit(environment) %}
    {%- do EqualStaticVariableInit(environment) %}
    {%- do ExistsStaticVariableInit(environment) %}
    {%- do ExpressionDefStaticVariableInit(environment) %}
    {%- do ExpressionRefStaticVariableInit(environment) %}
    {%- do FirstStaticVariableInit(environment) %}
    {%- do FunctionDefStaticVariableInit(environment) %}
    {%- do FunctionRefStaticVariableInit(environment) %}
    {%- do GreaterOrEqualStaticVariableInit(environment) %}
    {%- do GreaterStaticVariableInit(environment) %}
    {%- do IdentifierRefStaticVariableInit(environment) %}
    {%- do IfStaticVariableInit(environment) %}
    {%- do InIntervalStaticVariableInit(environment) %}
    {%- do InStaticVariableInit(environment) %}
    {%- do IntervalStaticVariableInit(environment) %}
    {%- do IntervalTypeSpecifierStaticVariableInit(environment) %}
    {%- do InValueSetStaticVariableInit(environment) %}
    {%- do IsNullStaticVariableInit(environment) %}
    {%- do LastStaticVariableInit(environment) %}
    {%- do LessOrEqualStaticVariableInit(environment) %}
    {%- do LessStaticVariableInit(environment) %}
    {%- do LetClauseStaticVariableInit(environment) %}
    {%- do ListStaticVariableInit(environment) %}
    {%- do ListTypeSpecifierStaticVariableInit(environment) %}
    {%- do LiteralStaticVariableInit(environment) %}
    {%- do MaxValueStaticVariableInit(environment) %}
    {%- do MinValueStaticVariableInit(environment) %}
    {%- do MultiplyStaticVariableInit(environment) %}
    {%- do NamedTypeSpecifierStaticVariableInit(environment) %}
    {%- do NegateStaticVariableInit(environment) %}
    {%- do NotStaticVariableInit(environment) %}
    {%- do NullStaticVariableInit(environment) %}
    {%- do OperandDefStaticVariableInit(environment) %}
    {%- do OperandRefStaticVariableInit(environment) %}
    {%- do OrStaticVariableInit(environment) %}
    {%- do ParameterRefStaticVariableInit(environment) %}
    {%- do PropertyStaticVariableInit(environment) %}
    {%- do QuantityStaticVariableInit(environment) %}
    {%- do QueryLetRefStaticVariableInit(environment) %}
    {%- do QueryStaticVariableInit(environment) %}
    {%- do RetrieveStaticVariableInit(environment) %}
    {%- do ReturnClauseStaticVariableInit(environment) %}
    {%- do SingletonFromStaticVariableInit(environment) %}
    {%- do SortClauseStaticVariableInit(environment) %}
    {%- do StartStaticVariableInit(environment) %}
    {%- do SubtractStaticVariableInit(environment) %}
    {%- do TimezoneOffsetFromStaticVariableInit(environment) %}
    {%- do ToDateStaticVariableInit(environment) %}
    {%- do ToDecimalStaticVariableInit(environment) %}
    {%- do TupleElementStaticVariableInit(environment) %}
    {%- do TupleStaticVariableInit(environment) %}
    {%- do TypeSpecifierStaticVariableInit(environment) %}
    {%- do UnionStaticVariableInit(environment) %}
    {%- do UnsupportedStaticVariableInit(environment) %}
    {%- do ValueSetDefStaticVariableInit(environment) %}
    {%- do ValueSetRefStaticVariableInit(environment) %}
    {%- do WithStaticVariableInit(environment) %}
{%- endmacro %}