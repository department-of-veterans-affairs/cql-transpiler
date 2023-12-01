{%- from "library/globals/DataSQLFormatEnum.sql" import DataSQLFormatEnumInit %}
{%- from "library/globals/DataStateEnum.sql" import DataStateEnumInit %}
{%- from "library/globals/AccessTypeEnum.sql" import AccessTypeEnumInit %}
{%- from "library/globals/ListPrintingFunctions.sql" import ListPrintingFunctionsInit %}
{%- from "library/globals/RequiresContextEnum.sql" import RequiresContextEnumInit %}

{%- macro OperatorClassGetDataState(environment, this, state, arguments, carrier) %}
    {%- set carrier.value = this.defaultDataState %}
    {%- if carrier.value == environment.DataStateEnum.INHERITED %}
        {%- if arguments['child'] %}
            {%- set inheritFrom = arguments['child'] %}
        {%- elif arguments['referenceTo'] %}
            {%- set inheritFrom = arguments['referenceTo'] %}
        {%- endif %}
        {%- if inheritFrom %}
            {%- do inheritFrom['operator'].getDataState(environment, inheritFrom['operator'], state, inheritFrom, carrier) %}
        {%- elif arguments['children'] %}
            {%- for child in arguments['children'] %}
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
        {%- endif %}
    {%- endif -%}

    {%- if carrier.value in [none, Undefined, '', environment.DataStateEnum.UNDETERMINED, environment.DataStateEnum.INHERITED]  -%}
        {{ environment.logError(environment, "Unable to determine data state for operator "~this) }}
    {%- endif %}
{%- endmacro %}

{%- macro OperatorClassGetDataSQLFormat(environment, this, state, arguments, carrier) %}
    {%- set carrier.value = this.defaultDataSQLFormat %}
    {%- if carrier.value == environment.DataSQLFormatEnum.INHERITED %}
        {%- if arguments['child'] %}
            {%- set inheritFrom = arguments['child'] %}
        {%- elif arguments['referenceTo'] %}
            {%- set inheritFrom = arguments['referenceTo'] %}
        {%- endif %}

        {%- if inheritFrom %}
            {%- do inheritFrom['operator'].getDataSQLFormat(environment, inheritFrom['operator'], state, inheritFrom, carrier) %}
        {%- endif %}
    {%- endif %}

    {%- if carrier.value in [none, Undefined, '', environment.DataSQLFormatEnum.UNDETERMINED, environment.DataSQLFormatEnum.INHERITED]  -%}
        {{ environment.logError(environment, "Unable to determine data format for operator "~this) }}
    {%- endif %}
{%- endmacro %}

{%- macro OperatorClassGetAccessType(environment, this, state, arguments, carrier) %}
    {%- set carrier.value = this.defaultAccessType %}

    {%- if carrier.value in [environment.AccessTypeEnum.INHERITED, environment.AccessTypeEnum.AUTO] %}
        {%- if arguments['child'] %}
            {%- set inheritFrom = arguments['child'] %}
        {%- elif arguments['referenceTo'] %}
            {%- set inheritFrom = arguments['referenceTo'] %}
        {%- endif %}

        {%- if inheritFrom %}
            {%- do inheritFrom['operator'].getAccessType(environment, inheritFrom['operator'], state, inheritFrom, carrier) %}
        {%- endif %}

        {%- if this.defaultAccessType == environment.AccessTypeEnum.AUTO %}
            {%- set dataSQLFormatCarrier = namespace() %}
            {%- do inheritFrom['operator'].getDataSQLFormat(environment, inheritFrom['operator'], state, inheritFrom, dataSQLFormatCarrier) %}
            {%- if dataSQLFormatCarrier in [none, Undefined, environment.DataSQLFormatEnum.UNDETERMINED] %}
                {{ 0/0 }}
            {%- elif (carrier.value == environment.AccessTypeEnum.DOT_PROPERTY) and not (dataSQLFormatCarrier.value in [environment.DataSQLFormatEnum.RAW_VALUE, environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE_REFERENCE, environment.DataSQLFormatEnum.QUERY_REFERENCE]) %}
                {%- set carrier.value = environment.AccessTypeEnum.SELECT_FROM %}
            {%- endif %}
        {%- endif %}
    {%- endif %}

    {%- if carrier.value in [none, Undefined, '', environment.AccessTypeEnum.INHERITED, environment.AccessTypeEnum.AUTO]  -%}
        {{ environment.logError(environment, "Unable to determine access type for operator "~this) }}
        {{0/0}}
    {%- endif %}
{%- endmacro %}

{%- macro OperatorClassGetRequiresContext(environment, this, state, arguments, carrier) -%}
    {%- set carrier.value = this.defaultRequiresContext %}
    {%- if carrier.value == environment.RequiresContextEnum.INHERITED_OR_FALSE %}
        {#- by default, we don't require context #}
        {%- set carrier.value = environment.RequiresContextEnum.FALSE %}

        {%- if arguments['child'] %}
            {%- set inheritFrom = arguments['child'] %}
        {%- elif arguments['referenceTo'] %}
            {%- set inheritFrom = arguments['referenceTo'] %}
        {%- endif %}

        {%- if inheritFrom %}
            {%- do inheritFrom['operator'].getRequiresContext(environment, inheritFrom['operator'], state, inheritFrom, carrier) %}
        {%- elif arguments['children'] %}
            {%- for child in arguments['children'] %}
                {%- set childStateCarrier = namespace() %}
                {%- do child['operator'].getRequiresContext(environment, child['operator'], state, child, childStateCarrier) %}
                {%- if childStateCarrier.value == environment.RequiresContextEnum.TRUE %}
                {%- set carrier.value = environment.RequiresContextEnum.TRUE %}
                {%- endif %}
            {%- endfor %}
        {%- elif arguments['left'] and arguments['right'] %}
            {%- for child in [arguments['left'], arguments['right']] %}
                {%- set childStateCarrier = namespace() %}
                {%- do child['operator'].getRequiresContext(environment, child['operator'], state, child, childStateCarrier) %}
                {%- if childStateCarrier.value == environment.RequiresContextEnum.TRUE %}
                {%- set carrier.value = environment.RequiresContextEnum.TRUE %}
                {%- endif %}
            {%- endfor %}
        {%- endif %}
    {%- endif -%}
{%- endmacro -%}

{%- macro OperatorClassPrint(environment, this, state, arguments) -%}
    /* Operator with state: <{{ state }}>, arguments: <{{ arguments }}>
    {%- if arguments['child'] -%}
        , child:<*/{{ this.print(environment, this, state, arguments['child']) }}/*>
    {%- endif %}
    {%- if arguments['children'] -%}
        , children:<*/{{ environment.printOperatorsFromList(environment, state, arguments['children'], " /* break */ ") }}/*>
    {%- endif %}
    {%- if arguments['left'] -%}
    , left:<*/{{ arguments['left']['operator'].print(environment, arguments['left']['operator'], state, arguments['left']) }}/*>
    {%- endif %}
    {%- if arguments['right'] -%}
    , right:<*/{{ arguments['right']['operator'].print(environment, arguments['right']['operator'], state, arguments['right']) }}/*>
    {%-     endif -%}
    */
{%- endmacro %}

{%- macro OperatorClassConstruct(environment, state, operatorNamespace) %}
    {%- set operatorNamespace.defaultDataState = environment.DataStateEnum.UNDETERMINED %}
    {%- set operatorNamespace.defaultDataSQLFormat = environment.DataSQLFormatEnum.UNDETERMINED %}
    {%- set operatorNamespace.defaultAccessType = environment.AccessTypeEnum.UNDETERMINED %}
    {%- set operatorNamespace.defaultRequiresContext = environment.RequiresContextEnum.INHERITED_OR_FALSE %}
    {%- set operatorNamespace.getDataSQLFormat = OperatorClassGetDataSQLFormat %}
    {%- set operatorNamespace.getDataState = OperatorClassGetDataState %}
    {%- set operatorNamespace.getAccessType = OperatorClassGetAccessType %}
    {%- set operatorNamespace.getRequiresContext = OperatorClassGetRequiresContext %}
    {%- set operatorNamespace.print = OperatorClassPrint %}
{%- endmacro %}

{%- macro OperatorClassInit(environment) %}
    {#-  initialize prerequisites #}
    {%- do DataStateEnumInit(environment) %}
    {%- do DataSQLFormatEnumInit(environment) %}
    {%- do AccessTypeEnumInit(environment) %}
    {%- do ListPrintingFunctionsInit(environment) %}
    {%- do RequiresContextEnumInit(environment) %}
    {#-  initialize OperatorClass #}
    {%- set OperatorClass = namespace() %}
    {%- set OperatorClass.construct = OperatorClassConstruct %}
    {%- set OperatorClass.getDataState = OperatorClassGetDataState %}
    {%- set OperatorClass.getDataSQLFormat = OperatorClassGetDataSQLFormat %}
    {%- set OperatorClass.getAccessType = OperatorClassGetAccessType %}
    {%- set OperatorClass.print = OperatorClassPrint %}
    {%- set environment.OperatorClass = OperatorClass %}
{%- endmacro %}