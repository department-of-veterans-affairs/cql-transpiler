{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}

{%- macro OperatorClassGetDataType(environment, this, carrier, state, arguments) %}
    {%- set carrier.value = this.defaultDataType %}
    {%- if carrier.value == environment.DataTypeEnum.INHERITED and arguments and arguments['child'] %}
        {% set carrier.value = arguments['child']['operator'].getDataType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
    {%- elif carrier.value == environment.DataTypeEnum.INHERITED and arguments and arguments['referenceTo'] %}
            {% set carrier.value = arguments['referenceTo']['operator'].getDataType(environment, arguments['referenceTo']['operator'], carrier, state, arguments['referenceTo']) %}
    {%- endif %}
{%- endmacro %}

{%- macro OperatorClassAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- set carrier.value = this.allowsSelectFromAccessTypeByDefault %}
{%- endmacro %}

{%- macro OperatorClassAllowsDotPropertyAccessType(environment, this, carrier, state, arguments) %}
    {%- set carrier.value = this.allowsDotPropertyAccessTypeByDefault %}
{%- endmacro %}

{%- macro OperatorClassPrint(environment, this, state, arguments) -%}
    /* Operator with state: <{{ state }}>, arguments: <{{ arguments }}>
    {%- if arguments['child'] -%}
        , child:<*/{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}/*>
    {%- endif %}
    {%- if arguments['children'] -%}
        , children:<*/{{ environment.printOperatorsFromList(environment, this, state, arguments['children'], " /* break */ ") }}/*>
    {%- endif %}
    {%- if arguments['left'] -%}
    , left:<*/{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['left']) }}/*>
    {%- endif %}
    {%- if arguments['right'] -%}
    , right:<*/{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }}/*>
    {%-     endif -%}
    */
{%- endmacro %}

{%- macro OperatorClassConstruct(environment, state, operatorNamespace) %}
    {%- set operatorNamespace.allowsDotPropertyAccessTypeByDefault = false %}
    {%- set operatorNamespace.allowsSelectFromAccessTypeByDefault = false %}
    {%- set operatorNamespace.defaultDataType = environment.DataTypeEnum.SIMPLE %}
    {%- set operatorNamespace.allowsSelectFromAccessType = OperatorClassAllowsSelectFromAccessType %}
    {%- set operatorNamespace.allowsDotPropertyAccessType = OperatorClassAllowsDotPropertyAccessType %}
    {%- set operatorNamespace.getDataType = OperatorClassGetDataType %}
    {%- set operatorNamespace.print = OperatorClassPrint %}
{%- endmacro %}

{%- macro OperatorClassInit(environment) %}
    {#-  initialize prerequisites #}
    {%- do DataTypeEnumInit(environment) %}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {#-  initialize OperatorClass #}
    {%- set OperatorClass = namespace() %}
    {%- set OperatorClass.construct = OperatorClassConstruct %}
    {%- set environment.OperatorClass = OperatorClass %}
{%- endmacro %}