
{#
    This file should be supplied to a folder where an intermediate AST rendered in jinja so on compilation its contents are rendered as SparkSQL.
#}
{%- import'jinja_transpilation_libraries/sparksql/_globals_sparksql.j2' as _globals %}

{%- macro printOperator(state, operatorArguments) %}
{{ _globals.OperatorHandler.print(state, operatorArguments) }}
{%- endmacro %}

{# OPERATOR IMPLEMENTATIONS #}

{# Add operator #}
{%- set Add = namespace() %}
{%- macro printAdd(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} + {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Add) %}
{%- set Add.print = printAdd %}

{# As operator #}
{%- set As = namespace() %}
{%- macro printAs(this, state, arguments) %}
{#-     arguments['typeSpecifier'] is unused #}
{{ printOperator(state, arguments['child'])}}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(As) %}
{%- set As.print = printAs %}

{# After operator #}
{%- set After = namespace() %}
{%- macro printAfter(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} > {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(After) %}
{%- set After.print = printAfter %}

{# AliasedQuerySource operator #}
{%- set AliasedQuerySource = namespace() %}
{%- macro printAliasedQuerySource(this, state, arguments) %}
{{ printOperator(state, arguments['child']) }} AS {{ arguments['alias'] }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(AliasedQuerySource) %}
{%- set AliasedQuerySource.print = printAliasedQuerySource %}

{# AliasRef operator #}
{%- set AliasRef = namespace() %}
{%- macro printAliasRef(this, state, arguments) %}
{{ arguments['name'] }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(AliasRef) %}
{%- set AliasRef.print = printAliasRef %}

{# And operator #}
{%- set And = namespace() %}
{%- macro printAnd(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} AND {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(And) %}
{%- set And.print = printAnd %}

{# Before operator #}
{%- set Before = namespace() %}
{%- macro printBefore(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} < {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Before) %}
{%- set Before.print = printBefore %}

{# ByExpression operator #}
{%- set ByExpression = namespace() %}
{%- macro printByExpression(this, state, arguments) %}
{{ printOperator(state, arguments['child']) }} {{ arguments['direction'] }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(ByExpression) %}
{%- set ByExpression.defaultDataType = _globals.DataType.STATEMENT %}
{%- set ByExpression.print = printByExpression %}

{# CalculateAgeAt operator #}
{%- set CalculateAgeAt = namespace() %}
{%- macro printCalculateAgeAt(this, state, arguments) %}
floor(months_between({{ printOperator(state, arguments['right'])}}, {{ printOperator(state, arguments['left']) }}) / 12)
{%- endmacro %}
{%- do _globals.OperatorClass.construct(CalculateAgeAt) %}
{%- set CalculateAgeAt.print = printCalculateAgeAt %}

{# Coalesce operator #}
{%- set Coalesce = namespace() %}
{%- macro printCoalesce(this, state, arguments) %}
coalesce({{ _globals.printOperatorsFromList(state, arguments['children'], ', ') }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Coalesce) %}
{%- set Coalesce.defaultDataType = _globals.DataType.SIMPLE %}
{%- set Coalesce.print = printCoalesce %}

{# Concatenate operator #}
{%- set Concatenate = namespace() %}
{%- macro printConcatenate(this, state, arguments) %}
concat({{ _globals.printOperatorsFromList(state, arguments['children'], ", ") }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Concatenate) %}
{%- set Concatenate.print = printConcatenate %}

{# Count operator #}
{% set Count = namespace() %}
{%- macro printCount(this, state, arguments) %}
{%-     if state.context == 'Unfiltered' %}
SELECT count(*) FROM ({{ printOperator(state, arguments['child']) }})
{%-     else %}
SELECT {{ printIDFromContext(state.context) }}, count(*) FROM ({{ printOperator(state, arguments['child']) }}) GROUP BY {{ printIDFromContext(state.context) }}
{%-     endif %}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Count) %}
{% set Count.defaultDataType = _globals.DataType.TABLE %}
{% set Count.print = printCount %}

{# DateFrom operator #}
{%- set DateFrom = namespace() %}
{%- macro printDateFrom(this, state, arguments) %}
{{ printOperator(state, arguments['child']) }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(DateFrom) %}
{%- set DateFrom.print = printDateFrom %}

{# DateTime operator #}
{%- set DateTime = namespace() %}
{%- macro printDateTime(this, state, arguments) %}
{{ printOperator(state, arguments['year']) }}-{{ printOperator(state, arguments['month']) }}-{{ printOperator(state, arguments['day']) }}T{{ printOperator(state, arguments['hour']) }}:{{ printOperator(state, arguments['minute']) }}:{{ printOperator(state, arguments['second']) }}.{{ printOperator(state, arguments['millisecond']) }}Z
{%- endmacro %}
{%- do _globals.OperatorClass.construct(DateTime) %}
{%- set DateTime.print = printDateTime %}

{# DifferenceBetween operator #}
{%- set DifferenceBetween = namespace() %}
{%- macro printDifferenceBetween(this, state, arguments) %}
DATEDIFF({{ printOperator(state, arguments['right'])}}, {{ printOperator(state, arguments['left']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(DifferenceBetween) %}
{%- set DifferenceBetween.print = printDifferenceBetween %}

{# Divide operator #}
{%- set Divide = namespace() %}
{% macro printDivide(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} / {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Divide) %}
{%- set Divide.print = printDivide %}

{# End operator #}
{%- set End = namespace() %}
{%- macro printEnd(this, state, arguments) %}
{{ printOperator(state, arguments['child']) }}.{{ intervalEnd }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(End) %}
{%- set End.print = printEnd %}

{# Equal operator #}
{%- set Equal = namespace() %}
{%- macro printEqual(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} = {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Equal) %}
{%- set Equal.print = printEqual %}

{# Exists operator #}
{%- set Exists = namespace() %}
{%- macro printExists(this, state, arguments) %}
EXISTS ({{ printOperator(state, arguments['child'])}})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Exists) %}
{%- set Exists.print = printExists %}

{# ExpressionDef operator #}
{%- set ExpressionDef = namespace() %}
{%- macro printExpressionDef(this, state, arguments) %}
{%-     set previousContext = state.context %}
{%-     if previousContext == none and arguments['context'] != none %}
{%-         set state.context = arguments['context'] %}
{%-     endif %}
{{ printOperator(state, arguments['child']) }}
{%-     set state.context = previousContext %}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(ExpressionDef) %}
{%- set ExpressionDef.defaultDataType = _globals.DataType.INHERITED %}
{%- set ExpressionDef.print = printExpressionDef %}

{# ExpressionRef operator #}
{%- set ExpressionRef = namespace() %}
{%- macro printExpressionRef(this, state, arguments) %}
{{ arguments['reference'](state) }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(ExpressionRef) %}
{%- set ExpressionRef.defaultDataType = _globals.DataType.INHERITED %}
{%- set ExpressionRef.print = printExpressionRef %}

{# FunctionDef operator #}
{% set FunctionDef = namespace() %}
{%- macro printFunctionDef(this, state, arguments) %}
{#-     arguments['typeSpecifier'] is unused #}
{%-     set previousOperandsMatchedToFunctionArguments = state.operandsMatchedToFunctionArguments %}
{%-     set state.operandsMatchedToFunctionArguments = {} %}
{%-     for item in state.functionArguments %}
{%-         do state.operandsMatchedToFunctionArguments.update({ arguments.operators[loop.index - 1].name: item }) %}
{%-     endfor %}
{{ printOperator(state, arguments['child']) }}
{%-     set state.operandsMatchedToFunctionArguments = previousOperandsMatchedToFunctionArguments %}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(FunctionDef) %}
{%- set FunctionDef.defaultDataType = _globals.DataType.INHERITED %}
{%- set FunctionDef.print = printFunctionDef %}

{# FunctionRef operator #}
{%- set FunctionRef = namespace() %}
{%- macro printFunctionRef(this, state, arguments) %}
{%-     set previousFunctionArguments = state.functionArguments %}
{%-     set state.functionArguments = arguments['children'] %}
{{ arguments['reference'](state) }}
{%-     set state.functionArguments = previousFunctionArguments %}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(FunctionRef) %}
{%- set FunctionRef.defaultDataType = _globals.DataType.INHERITED %}
{%- set FunctionRef.print = printFunctionRef %}

{# Greater operator #}
{%- set Greater = namespace() %}
{%- macro printGreater(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} > {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Greater) %}
{%- set Greater.print = printGreater %}

{# GreaterOrEqual operator #}
{%- set GreaterOrEqual = namespace() %}
{%- macro printGreaterOrEqual(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} >= {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(GreaterOrEqual) %}
{%- set GreaterOrEqual.print = printGreaterOrEqual %}

{# IdentifierRef operator #}
{%- set IdentifierRef = namespace() %}
{%- macro printIdentifierRef(this, state, arguments) %}
{{ arguments['name'] }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(IdentifierRef) %}
{%- set IdentifierRef.print = printIdentifierRef %}

{# In operator #}
{%- set In = namespace() %}
{%- macro printIn(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} IN {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(In) %}
{%- set In.print = printIn %}

{# InInterval operator #}
{%- set InInterval = namespace() %}
{%- macro printInInterval(this, state, arguments) %}
{{ printOperator(state, arguments['left'])}} BETWEEN SELECT {{ intervalStart }} FROM {{ printOperator(state, arguments['right']) }} AND SELECT {{ intervalEnd }} FROM {{ printOperator(state, arguments['right']) }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(InInterval) %}
{%- set InInterval.print = printInInterval %}

{# Interval operator #}
{%- set Interval = namespace() %}
{%- macro printInterval(this, state, arguments) %}
{# TODO: high closed expression, low closed expression, struct formatting pass #}
SELECT struct({{ printOperator(state, arguments['high'])}} as {{ intervalEnd }}, {{ printOperator(state, arguments['low']) }} as {{ intervalStart }}) {{ _globals.printSingleValueColumnName() }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Interval) %}
{%- set Interval.print = printInterval %}
{%- set Interval.defaultDataType = _globals.DataType.ENCAPSULATED %}

{# If operator #}
{%- set If = namespace() %}
{%- macro printIf(this, state, arguments) %}
{%-             set previousCoercionInstructions = state.coercionInstructions %}
{%-             set state.coercionInstructions = {} %}
{#- conditional statement shouldn't be coerced #}
IF ({{ printOperator(state, arguments['condition'])}})
{%-             set state.coercionInstructions = previousCoercionInstructions %}
 THEN ({{ printOperator(state, arguments['then'])}}) ELSE ({{ printOperator(state, arguments['else'])}})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(If) %}
{%- set If.print = printIf %}
{%- set If.defaultDataType = _globals.DataType.INHERITED %}

{# Less operator #}
{%- set Less = namespace() %}
{%- macro printLess(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} < {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Less) %}
{%- set Less.print = printLess %}

{# LessOrEqual operator #}
{%- set LessOrEqual = namespace() %}
{%- macro printLessOrEqual(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} <= {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(LessOrEqual) %}
{%- set LessOrEqual.print = printLessOrEqual %}

{# LetClause operator #}
{%- set LetClause = namespace() %}
{%- macro printLetClause(this, state, arguments) %}
{{ printOperator(state, arguments['child']) }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(LetClause) %}
{%- set LetClause.defaultDataType = _globals.DataType.INHERITED %}
{%- set LetClause.print = printLetClause %}

{# List operator #}
{%- set List = namespace() %}
{%- macro printList(this, state, arguments) %}
SELECT collect_list({{ _globals.printSingleValueColumnName() }}) AS {{ _globals.printSingleValueColumnName() }} FROM (
{%-     if arguments['children']|length == 0 %}
{{ printEmptyTable() }}
{%-     else %}
{%-         set previousCoercionInstructions = state.coercionInstructions %}
{%-         set state.coercionInstructions = { _globals.DataType.SIMPLE: _globals.DataType.ENCAPSULATED } %}
{{ _globals.printOperatorsFromList(state, arguments['children'], ' UNION ') }}
{%-         set state.coercionInstructions = previousCoercionInstructions %}
{%-     endif %})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(List) %}
{%- set List.defaultDataType = _globals.DataType.ENCAPSULATED %}
{%- set List.print = printList %}

{# Literal operator #}
{%- set Literal = namespace() %}
{%- macro printLiteral(this, state, arguments) %}
{%-     if arguments['type'] == 'Integer' %}
{{ arguments['value'] }}
{%-     elif arguments['type'] == 'String' %}
'{{ arguments['value'] }}'
{%-     else %}
{{ arguments['type'] }}::{{ arguments['value'] }}
{%-     endif %}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Literal) %}
{%- set Literal.print = printLiteral %}

{# Multiply operator #}
{%- set Multiply = namespace() %}
{%- macro printMultiply(this, state, arguments) %}
({{ printOperator(state, arguments['left']) }} * {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Multiply) %}
{%- set Multiply.print = printMultiply %}

{# Negate operator #}
{%- set Negate = namespace() %}
{%- macro printNegate(this, state, arguments) %}
-{{ printOperator(state, arguments['child']) }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Negate) %}
{%- set Negate.print = printNegate %}

{# Not operator #}
{%- set Not = namespace() %}
{%- macro printNot(this, state, arguments) %}
NOT {{ printOperator(state, arguments['child']) }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Not) %}
{%- set Not.print = printNot %}

{# Null operator #}
{%- set Null = namespace() %}
{%- macro printNull(this, state, arguments) %}
NULL
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Null) %}
{%- set Null.print = printNull %}

{# OperandRef operator #}
{%- set OperandRef = namespace() %}
{%- macro printOperandRef(this, state, arguments) %}
{{ printOperator(state, state.operandsMatchedToFunctionArguments[arguments['reference']]) }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(OperandRef) %}
{%- set OperandRef.defaultDataType = _globals.DataType.INHERITED %}
{%- set OperandRef.print = printOperandRef %}

{# Or operator #}
{%- set Or = namespace() %}
{%- macro printOr(this, state, arguments) %}
({{ printOperator(state, arguments['left'])}} OR {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Or) %}
{%- set Or.print = printOr %}

{# ParameterRef operator #}
{%- set ParameterRef = namespace() %}
{%- macro printParameterRef(this, state, arguments) %}
@{{ arguments['name'] }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(ParameterRef) %}
{%- set ParameterRef.print = printParameterRef %}

{# Property operator #}
{%- set Property = namespace() %}
{%- macro printProperty(this, state, arguments) %}
{%-     if arguments['child'] != none %}
{{ printOperator(state, arguments['child']) }}.{{ arguments['path'] }}
{%-     elif arguments['path'] != none %}
{{ arguments['scope'] }}.{{ arguments['path'] }}
{%-     else %}
{{ arguments['scope'] }}
{%-     endif %}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Property) %}
{%- set Property.defaultDataType = _globals.DataType.ENCAPSULATED %}
{%- set Property.print = printProperty %}

{# Query operator #}
{%- set Query = namespace() %}
{%- macro printQuery(this, state, arguments) %}
{%-     set state.insideQuery = true %}
{%-     set previousCoercionInstructions = state.coercionInstructions %}
{%-     set state.coercionInstructions = {} %}
{%-     if arguments['letClauseList']|length > 0 %}
LET {{ _globals.printOperatorsFromList(state, arguments['letClauseList'], ", ") }} 
{%-     endif %}
SELECT
{%-     if arguments['returnClause'] == none %}
 *
{%-     else %}
 {{ printOperator(state, arguments['returnClause']) }} 
{%-     endif %}
 FROM ({{ _globals.printOperatorsFromList(state, arguments['children'], ", ") }})
{%-     if arguments['where'] != none %}
 WHERE {{ printOperator(state, arguments['where']) }}
{%-     endif %}
{%-     if arguments['sortClause'] != none %}
 {{ printOperator(state, arguments['sortClause']) }}
{%-     endif %}
{%-     set state.coercionInstructions = previousCoercionInstructions %}
{%-     set state.insideQuery = false %}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Query) %}
{%- set Query.defaultDataType = _globals.DataType.TABLE %}
{%- set Query.print = printQuery %}

{# Retrieve operator #}
{%- set Retrieve = namespace() %}
{%- do _globals.OperatorClass.construct(Retrieve) %}
{%- set Retrieve.defaultDataType = _globals.DataType.TABLE %}
{%- set Retrieve.print = _globals.printUnimplemented %}

{# ReturnClause operator #}
{%- set ReturnClause = namespace() %}
{%- macro printReturnClause(this, state, arguments) %}
{{ _globals.printOperatorsFromList(state, arguments['children'], ", ") }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(ReturnClause) %}
{%- set ReturnClause.defaultDataType = _globals.DataType.UNKNOWN %}
{%- set ReturnClause.print = printReturnClause %}

{# SortClause operator #}
{%- set SortClause = namespace() %}
{%- macro printSortClause(this, state, arguments) %}
ORDER BY {{ _globals.printOperatorsFromList(state, arguments['children'], ", ") }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(SortClause) %}
{%- set SortClause.defaultDataType = _globals.DataType.STATEMENT %}
{%- set SortClause.print = printSortClause %}

{# Start operator #}
{%- set Start = namespace() %}
{%- macro printStart(this, state, arguments) %}
{{ printOperator(state, arguments['child']) }}.{{ intervalStart }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Start) %}
{%- set Start.print = printStart %}

{# Subtract operator #}
{%- set Subtract = namespace() %}
{%- macro printSubtract(this, state, arguments) %}
({{ printOperator(state, arguments['left']) }} - {{ printOperator(state, arguments['right']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Subtract) %}
{%- set Subtract.print = printSubtract %}

{# ToDate operator #}
{%- set ToDate = namespace() %}
{%- macro printToDate(this, state, arguments) %}
to_date({{ printOperator(state, arguments['child']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(ToDate) %}
{%- set ToDate.print = printToDate %}

{# ToDecimal operator #}
{%- set ToDecimal = namespace() %}
{%- macro printToDecimal(this, state, arguments) %}
(0.0 + {{ printOperator(state, arguments['child']) }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(ToDecimal) %}
{%- set ToDecimal.print = printToDecimal %}

{# Tuple operator #}
{%- set Tuple = namespace() %}
{%- macro printTuple(this, state, arguments) %}
SELECT struct(*) FROM ({{ _globals.printOperatorsFromList(state, arguments['children'], ', ') }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Tuple) %}
{%- set Tuple.defaultDataType = _globals.DataType.ENCAPSULATED %}
{%- set Tuple.print = printTuple %}

{# TupleElement operator #}
{%- set TupleElement = namespace() %}
{%- macro printTupleElement(this, state, arguments) %}
{%-     set previousCoercionInstructions = state.coercionInstructions %}
{%-     set state.coercionInstructions = { _globals.DataType.TABLE: _globals.DataType.ENCAPSULATED, _globals.DataType.SIMPLE: _globals.DataType.ENCAPSULATED } %}
{{ printOperator(state, arguments['child']) }} AS {{ arguments['name'] }}
{%-     set state.coercionInstructions = previousCoercionInstructions %}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(TupleElement) %}
{%- set TupleElement.defaultDataType = _globals.DataType.ENCAPSULATED %}
{%- set TupleElement.print = printTupleElement %}

{# Union operator #}
{%- set Union = namespace() %}
{%- macro printUnion(this, state, arguments) %}
{%-     set previousCoercionInstructions = state.coercionInstructions %}
{%-     set state.coercionInstructions = { _globals.DataType.ENCAPSULATED: _globals.DataType.TABLE } %}
{{ _globals.printOperatorsFromList(state, arguments['children'], ' UNION ') }}
{%-     set state.coercionInstructions = previousCoercionInstructions %}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Union) %}
{%- set Union.defaultDataType = _globals.DataType.TABLE %}
{%- set Union.print = printUnion %}

{# ValueSetRef operator #}
{%- set ValueSetRef = namespace() %}
{%- macro printValueSetRef(this, state, arguments) %}
{{ arguments['reference'] }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(ValueSetRef) %}
{%- set ValueSetRef.print = printValueSetRef %}


{# With operator #}
{%- set With = namespace() %}
{%- macro printWith(this, state, arguments) %}
WHERE (LET {{ printOperator(state, arguments['child'])}} AS {{ arguments['alias'] }} SELECT {{ printOperator(state, arguments['suchThat']) }} {{ _globals.printSingleValueColumnName() }})
{%- endmacro %}
{%- do _globals.OperatorClass.construct(With) %}
{%- set With.print = printWith %}
{%- set With.defaultDataType = _globals.DataType.STATEMENT %}

{# UNTESTED OPERATORS #}

{# SingletonFrom operator #}
{%- set SingletonFrom = namespace() %}
{%- macro printSingletonFrom(this, state, arguments) %}
{{ printOperator(state, arguments['child']) }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(SingletonFrom) %}
{%- set SingletonFrom.print = printSingletonFrom %}

{# Quantity operator #}
{%- set Quantity = namespace() %}
{%- macro printQuantity(this, state, arguments) %}
{%- if arguments['unit'] == 'year' %}
INTERVAL {{ arguments['value'] }} YEAR
{%- elif arguments['unit'] == 'month' %}
INTERVAL {{ arguments['value'] }} MONTH
{%- elif arguments['unit'] == 'day' %}
INTERVAL {{ arguments['value'] }} DAY
{%- elif arguments['unit'] == 'hour' %}
INTERVAL {{ arguments['value'] }} HOUR
{%- elif arguments['unit'] == 'minute' %}
INTERVAL {{ arguments['value'] }} MINUTE
{%- elif arguments['unit'] == 'second' %}
INTERVAL {{ arguments['value'] }} SECOND
{%- elif arguments['unit'] == 'millisecond' %}
INTERVAL {{ arguments['value'] }} MILLISECOND
{%- else %}
/* Unsupported Quantity: <{{ arguments['unit'] }}, {{ arguments['value'] }}> */
{%- endif %}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(Quantity) %}
{%- set Quantity.print = printQuantity %}

{# QueryLetRef operator #}
{%- set QueryLetRef = namespace() %}
{%- macro printQueryLetRef(this, state, arguments) %}
{{ arguments['name'] }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(QueryLetRef) %}
{%- set QueryLetRef.print = printQueryLetRef %}

{# InValueSet operator #}
{%- set InValueSet = namespace() %}
{%- macro printInValueSet(this, state, arguments) %}
/* Unsupported InValueSet
{%-     if arguments['child'] != none %}
 with child: <{{ printOperator(state, arguments['child'])}}>
{%-     endif %}
{%-     if arguments['valueSetReference'] == none %}
 valueSetExpression: <{{ printOperator(state, arguments['valueSetExpression'])}}>
{%-     else %}
 valueSetReference: <{{ printOperator(state, arguments['valueSetReference'])}}>
{%-     endif %}
*/
{%- endmacro %}
{%- do _globals.OperatorClass.construct(InValueSet) %}
{%- set InValueSet.defaultDataType = _globals.DataType.SIMPLE %}
{%- set InValueSet.print = printInValueSet %}

{# IsNull operator #}
{%- set IsNull = namespace() %}
{%- macro printIsNull(this, state, arguments) %}
{{ printOperator(state, arguments['child']) }} AS {{ arguments['alias'] }} SUCH THAT {{ arguments['suchThat'] }}
{%- endmacro %}
{%- do _globals.OperatorClass.construct(IsNull) %}
{%- set IsNull.defaultDataType = _globals.DataType.SIMPLE %}
{%- set IsNull.print = printIsNull %}

{#
    NONPRINTING OPERATORS

    Some varieties of operator have no SQL equivalent, but may be printed in debug text or inside comments
#}

{# TypeSpecifierClass #}

{% set TypeSpecifierClass = namespace() %}
{%- macro printTypeSpecifier(this, state, arguments) %}
{%-     set previousInsideSqlComment = state.insideSqlComment %}
{%-     set state.insideSqlComment = true %}
{%-     if not previousInsideSqlComment %}
/*
{%-     endif %}
 TypeSpecifier operator: <{{ this.name }}> with children:[{{ _globals.printOperatorsFromList(state, arguments['children'], ", ") }}] 
{%-     if not previousInsideSqlComment %}
*/
{%-     endif %}
{%-     set state.insideSqlComment = previousInsideSqlComment %}
{%- endmacro %}
{%- macro constructTypeSpecifierOperator(typeSpecifierOperatorNamespace) %}
{%-     do _globals.UnsupportedOperatorClass.construct(typeSpecifierOperatorNamespace) %}
{%-     set TypeSpecifierClass.name = "TypeSpecifier" %}
{%-     set typeSpecifierOperatorNamespace.print = printTypeSpecifier %}
{%- endmacro %}
{%- set TypeSpecifierClass.construct = constructTypeSpecifierOperator %}

{# TypeSpecifier operator #}
{%- set TypeSpecifier = namespace() %}
{%- do TypeSpecifierClass.construct(TypeSpecifier) %}
{%- set TypeSpecifier.name = 'TypeSpecifier' %}

{# IntervalTypeSpecifier operator #}
{%- set IntervalTypeSpecifier = namespace() %}
{%- do TypeSpecifierClass.construct(IntervalTypeSpecifier) %}
{%- set IntervalTypeSpecifier.name = 'IntervalTypeSpecifier' %}

{# ListTypeSpecifier operator #}
{%- set ListTypeSpecifier = namespace() %}
{%- do TypeSpecifierClass.construct(ListTypeSpecifier) %}
{%- set ListTypeSpecifier.name = 'ListTypeSpecifier' %}

{# NamedTypeSpecifier operator #}
{%- set NamedTypeSpecifier = namespace() %}
{%- do TypeSpecifierClass.construct(NamedTypeSpecifier) %}
{%- set NamedTypeSpecifier.name = 'NamedTypeSpecifier' %}