{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/ConversionHandlingFunctions.sql" import ConversionHandlingFunctionsInit %}

{%- macro IfGetDataState(environment, this, state, arguments, carrier) %}
    {%- set carrier.value = this.defaultDataState %}
    {%- for child in [arguments['then'], arguments['else']] %}
        {%- set childStateCarrier = namespace() %}
        {%- do child['operator'].getDataState(environment, child['operator'], state, child, childStateCarrier) %}
        {%- if childStateCarrier.value == environment.DataStateEnum.UNDETERMINED %}
            {#- do nothing #}
        {%- elif carrier.value in [environment.DataStateEnum.INHERITED, environment.DataStateEnum.NULL, environment.DataStateEnum.UNDETERMINED] %}
            {%- set carrier.value = childStateCarrier.value %}
        {%- elif (carrier.value == environment.DataStateEnum.SIMPLE) and (childStateCarrier.value in [environment.DataStateEnum.ENCAPSULATED, environment.DataStateEnum.TABLE]) %}
            {%- set carrier.value = childStateCarrier.value %}
        {%- elif (carrier.value == environment.DataStateEnum.ENCAPSULATED) and (childStateCarrier.value == environment.DataStateEnum.TABLE) %}
            {%- set carrier.value = childStateCarrier.value %}
        {%- endif %}
    {%- endfor %}
    {%- if carrier.value in [none, Undefined, '', environment.DataStateEnum.UNDETERMINED, environment.DataStateEnum.INHERITED]  -%}
        {{ 0 / 0}}
    {%- endif %}
{%- endmacro %}

{%- macro IfGetDataSQLFormat(environment, this, state, arguments, carrier) %}
    {%- set carrier.value = this.defaultDataSQLFormat %}
    {%- set dataStateCarrier = namespace() %}
    {%- do this.getDataState(environment, this, state, arguments, dataStateCarrier) %}
    {%- if dataStateCarrier.value == environment.DataStateEnum.TABLE %}
        {%- set carrier.value = environment.DataSQLFormatEnum.QUERY_REFERENCE %}
    {%- elif dataStateCarrier.value == environment.DataStateEnum.ENCAPSULATED %}
        {%- set carrier.value = environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE_REFERENCE %}
    {%- elif (dataStateCarrier.value == environment.DataStateEnum.SIMPLE) or (dataStateCarrier.value == environment.DataStateEnum.NULL) %}
        {%- set carrier.value = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- endif %}
{%- endmacro %}

{%- macro IfGetAccessType(environment, this, state, arguments, carrier) %}
    {%- set carrier.value = environment.AccessTypeEnum.UNDETERMINED %}
    {%- for child in [arguments['then'], arguments['else']] %}
        {%- set childAccessCarrier = namespace() %}
        {%- do child['operator'].getAccessType(environment, child['operator'], state, child, childAccessCarrier) %}
        {%- if childAccessCarrier.value == environment.AccessTypeEnum.UNDETERMINED %}
            {#- do nothing #}
        {%- elif (carrier.value in [environment.AccessTypeEnum.UNDETERMINED, environment.AccessTypeEnum.SELECT_FROM]) and (childAccessCarrier.value in [environment.AccessTypeEnum.SELECT_FROM, environment.AccessTypeEnum.DOT_PROPERTY]) %}
        {%- elif childAccessCarrier.value in [environment.AccessTypeEnum.INTERVAL] %}
            {%- set carrier.value = childAccessCarrier.value %}
        {%- else %}
            {#- TODO -#}
            {{ 0/0 }}
        {%- endif %}
    {%- endfor %}

    {%- if carrier.value in [none, Undefined, '', environment.AccessTypeEnum.INHERITED, environment.AccessTypeEnum.AUTO]  -%}
        {{ environment.logError(environment, "Unable to determine access type for operator "~this) }}
        {{0/0}}
    {%- endif %}
{%- endmacro %}

{%- macro IfGetRequiresContext(environment, this, state, arguments, carrier) %}
    {%- set carrier.value = this.defaultRequiresContext %}
    {%- for child in [arguments['condition'], arguments['then'], arguments['else']] %}
        {%- set childAccessCarrier = namespace() %}
        {%- do child['operator'].getRequiresContext(environment, child['operator'], state, child, childAccessCarrier) %}
        {%- if childAccessCarrier.value == environment.RequiresContextEnum.TRUE %}
            {%- set carrier.value = environment.RequiresContextEnum.TRUE %}
        {%- endif %}
    {%- endfor %}
{%- endmacro %}

{%- macro IfPrint(environment, this, state, arguments) -%}
    {%- set dataStateCarrier = namespace() -%}
    {%- do this.getDataState(environment, this, state, arguments, dataStateCarrier) %}
    {%- set dataSQLFormatCarrier = namespace() -%}
    {%- do this.getDataSQLFormat(environment, this, state, arguments, dataSQLFormatCarrier) %}

    {%- if dataSQLFormatCarrier.value == environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE -%}
        {{ environment.wrapAsSingleValueTable(
            environment,
            "IF("
                ~arguments['condition']['operator'].print(environment, arguments['condition']['operator'], state, arguments['condition'])
                ~", "
                ~environment.printOperatorsFromListCoercing(environment, state, [arguments['then'], arguments['else']], ', ', dataStateCarrier.value, true)
                ~")"
        ) }}
    {%- else -%}
        IF({{ arguments['condition']['operator'].print(environment, arguments['condition']['operator'], state, arguments['condition']) }}, {{ environment.printOperatorsFromListCoercing(environment, state, [arguments['then'], arguments['else']], ', ', dataStateCarrier.value, true) }})
    {%- endif %}
{%- endmacro %}

{%- macro IfStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do ConversionHandlingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set If = namespace() %}
    {%- set environment.If = If %}
    {%- do environment.OperatorClass.construct(environment, none, environment.If) %}
    {%- set If.getDataState = IfGetDataState %}
    {%- set If.getDataSQLFormat = IfGetDataSQLFormat %}
    {%- set If.getAccessType = IfGetAccessType %}
    {%- set If.getRequiresContext = IfGetRequiresContext %}
    {%- set If.print = IfPrint %}
{%- endmacro %}