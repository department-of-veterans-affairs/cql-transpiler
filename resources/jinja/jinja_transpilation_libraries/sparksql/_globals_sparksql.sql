{# UTILITIES, GLOBAL VARIABLES, PARENT CLASSES #}

{#
    Static Functions: Standards

    Members:
        Public:
           printEmptyTable()
#}

{# Prints the single value column name #}
{%- macro printSingleValueColumnName() %}
_val
{%- endmacro %}

{# Prints an empty table #}
{%- macro printEmptyTable() %}
SELECT * FROM (SELECT 1 {{ printSingleValueColumnName() }}) WHERE {{ printSingleValueColumnName() }} = 0
{%- endmacro %}

{#
    ENUM - DataType

    Values:
        SIMPLE
        ENCAPSULATED
        TABLE
        INHERITED
        STATEMENT
        UNKNOWN
#}

{%- set DataType = namespace() %}
{%- set DataType.SIMPLE = 'simple' %}
{%- set DataType.ENCAPSULATED = 'encapsulated' %}
{%- set DataType.TABLE = 'table' %}
{%- set DataType.INHERITED = 'inherited' %}
{%- set DataType.STATEMENT = 'statement' %}
{%- set DataType.UNKNOWN = 'unknown' %}

{#
    CLASS - State

    Members:
        Public:
            construct(stateNamespace)
            coercionInstructions
            context
            insideQuery
            functionArguments
            insideSqlComment
#}

{%- set StateClass = namespace() %}
{%- macro stateClassConstruct(stateNamespace) %}
{%-     set stateNamespace.coercionInstructions = {} %}
{%-     set stateNamespace.context = none %}
{%-     set stateNamespace.insideQuery = false %}
{%-     set stateNamespace.functionArguments = {} %}
{%-     set stateNamespace.insideSqlComment = false %}
{%- endmacro %}
{%- set StateClass.construct = stateClassConstruct %}

{#
    CLASS - Operator

    Members:
        Public:
            construct(operatorNamespace)
            print(this, state, arguments)
            getDataType(this, carrier, state, arguments)
#}

{%- set OperatorClass = namespace() %}
{%- macro operatorClassGetDataType(this, carrier, state, arguments) %}
{%-     set carrier.value = this.defaultDataType %}
{%- endmacro %}
{%- macro operatorClassPrint(this, state, arguments) %}
{%-     set previousInsideSqlComment = state.insideSqlComment %}
{%-     set state.insideSqlComment = true %}
{%-     if not previousInsideSqlComment %}
/* 
{%-     endif %}
Operator with state: {{ state }} and arguments: {{ arguments }} 
{%-     if not previousInsideSqlComment %}
 */ 
{%-     endif %}
{%-     set state.insideSqlComment = previousInsideSqlComment %}
{%- endmacro %}
{%- macro operatorClassConstruct(operatorNamespace) %}
{%-     set operatorNamespace.defaultDataType = DataType.SIMPLE %}
{%-     set operatorNamespace.getDataType = operatorClassGetDataType %}
{%-     set operatorNamespace.print = operatorClassPrint %}
{%- endmacro %}
{%- set OperatorClass.construct = operatorClassConstruct %}

{#
    STATIC FUNCTIONS - Context Handling

    Members:
        Public:printIDFromContext(context)
#}

{# Prints the id matching the current context #}
{%- macro printIDFromContext(context) %}
{{ context }}id
{%- endmacro %}

{#
    STATIC FUNCTIONS - SparkSQL Type Conversion

    Members:
        Public:
            coerce(originalType, targetType, state, arguments)
        Private:
            collect(context, toCollect)
            decollect(context, toDecollect)
            encapsulate(context, toEncapsulate)
#}

{# Wraps SQL statement in a collect block #}
{%- macro collect(context, toCollect)%}
SELECT collect_list(struct(*)) AS {{ printSingleValueColumnName() }} FROM ({{ toCollect }})
{%-     if context != 'Unfiltered' %}
 GROUP BY {{ printIDFromContext(context) }}
{%-     endif %}
{% endmacro %}

{# Wraps SQL statement in a decollect block #}
{%- macro decollect(context, toDecollect) %}
SELECT col.* FROM (SELECT explode(*) FROM ({{ toDecollect }}))
{%- endmacro %}

{# Wraps SQL statement in an encapsulate block #}
{%- macro encapsulate(context, toEncapsulate) %}
SELECT {{ toEncapsulate }} {{ printSingleValueColumnName() }}
{%- endmacro %}

{# Coerces the first applicable child of this node into the correct type #}
{%- macro coerce(originalType, targetType, state, arguments) %}
{%-     set previousCoercionInstructions = state.coercionInstructions %}
{%-     if targetType == DataType.ENCAPSULATED and originalType == DataType.SIMPLE %}
{%-         set state.coercionInstructions = {} %}
{{ encapsulate(context, arguments['operator'].print(arguments['operator'], state, arguments)) }}
{%-         set state.coercionInstructions = previousCoercionInstructions %}
{%-     elif targetType == DataType.ENCAPSULATED and originalType == DataType.TABLE %}
{%-         set state.coercionInstructions = {} %}
{{ collect(context, arguments['operator'].print(arguments['operator'], state, arguments)) }}
{%-         set state.coercionInstructions = previousCoercionInstructions %}
{%-     elif targetType == DataType.TABLE and originalType == DataType.ENCAPSULATED %}
{%-         set state.coercionInstructions = {} %}
{{ decollect(context, arguments['operator'].print(arguments['operator'], state, arguments)) }}
{%-         set state.coercionInstructions = previousCoercionInstructions %}
{%-     else %}
{{ arguments['operator'].print(arguments['operator'], state, arguments) }}
{%-     endif %}
{%- endmacro %}

{#
    CLASS - OperatorHandler

    Members:
        Public:
            construct(operatorHandlerNamespace)
            print(state, arguments)
#}

{%- set OperatorHandlerClass = namespace() %}

{%- macro operatorHandlerPrint(state, arguments) %}
{%-     if state == none %}
{%-         set state = namespace() %}
{%-         do StateClass.construct(state) %}
{%-     endif %}
{%-     set operatorDataTypeCarrier = namespace() %}
{%-     do arguments['operator'].getDataType(arguments['operator'], operatorDataTypeCarrier, state, arguments) %}
{{ coerce(operatorDataTypeCarrier.value, state.coercionInstructions[operatorDataTypeCarrier.value], state, arguments) }}
{%- endmacro %}

{%- macro OperatorHandlerConstruct(operatorHandlerNamespace) %}
{%-     set operatorHandlerNamespace.print = operatorHandlerPrint %}
{%- endmacro %}
{%- set OperatorHandlerClass.construct = OperatorHandlerConstruct %}

{#
    Global Variable - OperatorHandler
#}
{%- set OperatorHandler = namespace() %}
{%- do OperatorHandlerClass.construct(OperatorHandler) %}

{#
    STATIC FUNCTIONS - List Printing Utilities

    Members:
        Public:
            printItemsFromList(listOfItemsToPrint, joiner)
            printOperatorsFromList(state, listOfArgumentsToPrint, joiner)
#}

{# Prints items from a list, delimiting them with a joiner #}
{%- macro printItemsFromList(listOfItemsToPrint, joiner) %}
{%-     set ns = namespace(first = true) %}
{%-     for item in listOfItemsToPrint %}
{%-         if ns.first %}
{%-             set ns.first = false %}
{%-         else %}
{{ joiner }}
{%-         endif %}
{{ item }}
{%-     endfor %}
{%- endmacro %}

{# Prints operators from a list, delimiting them with a joiner #}
{%- macro printOperatorsFromList(state, listOfArgumentsToPrint, joiner) %}
{%-     set ns = namespace(first = true) %}
{%-     for item in listOfArgumentsToPrint %}
{%-         if ns.first %}
{%-             set ns.first = false %}
{%-         else %}
{{ joiner }}
{%-         endif %}
{{ OperatorHandler.print(state, item) }}
{%-     endfor %}
{%- endmacro %}

{#
    Class - UnsupportedOperator
#}
{% set UnsupportedOperatorClass = namespace() %}
{%- macro printDefault(this, state, arguments) %}
{%-     set previousInsideSqlComment = state.insideSqlComment %}
{%-     set state.insideSqlComment = true %}
{%-     if not previousInsideSqlComment %}
/*
{%-     endif %}
Unsupported Operator: <{% if arguments['unsupportedOperator'] != none or arguments['unsupportedOperator']|length == 0 %}{{ arguments['unsupportedOperator'] }}{% else %}{{ arguments }}{% endif %}> with arguments: <{{arguments}}> with children: <[{{ printOperatorsFromList(state, arguments['children'], ", ") }}]>
{%-     if not previousInsideSqlComment %}
*/
{%-     endif %}
{%-     set state.insideSqlComment = previousInsideSqlComment %}
{%- endmacro %}
{%- macro constructUnsupportedOperator(unsupportedOperatorNamespace) %}
{%-     do OperatorClass.construct(unsupportedOperatorNamespace) %}
{%-     set unsupportedOperatorNamespace.print = printDefault %}
{%- endmacro %}
{%- set UnsupportedOperatorClass.construct = constructUnsupportedOperator %}

{#
    Global Variable - Unsupported
#}
{}
{%- set Unsupported = namespace() %}
{%- do UnsupportedOperatorClass.construct(Unsupported) %}
{%- set Unsupported.defaultDataType = DataType.UNKNOWN %}
{#
    STATIC FUNCTIONS - Print unimplemented operator
#}
{%- macro printUnimplemented(this, state, arguments) -%}
{%      set previousInsideSqlComment = state.insideSqlComment -%}
{%      set state.insideSqlComment = true -%}
{%      if not previousInsideSqlComment %}/* {% endif -%}
Unimplemented Operator: {{ arguments['operator'] }} with arguments: {{arguments}} with children: [*/{{ printOperatorsFromList(state, arguments['children'], ", ") }}/*]
{%-     if not previousInsideSqlComment %} */{% endif -%}
{%      set state.insideSqlComment = previousInsideSqlComment -%}
{% endmacro %}

{#
    Global Variables - Interval support
#}

{%- set intervalStart = 'low' %}
{%- set intervalEnd = 'high' %}