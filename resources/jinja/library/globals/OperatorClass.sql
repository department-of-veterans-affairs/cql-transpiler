{%- macro OperatorClassGetDataType(environment, this, carrier, state, arguments) %}
    {%- set carrier.value = this.defaultDataType %}
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
    {%- set operatorNamespace.defaultDataType = environment.DataTypeEnum.SIMPLE %}
    {%- set operatorNamespace.getDataType = OperatorClassGetDataType %}
    {%- set operatorNamespace.print = OperatorClassPrint %}
{%- endmacro %}

{%- macro OperatorClassInit(environment) %}
    {%- set OperatorClass = namespace() %}
    {%- set OperatorClass.construct = OperatorClassConstruct %}
    {%- set environment.OperatorClass = OperatorClass %}
{%- endmacro %}