# Summary

# States:

* SIMPLE
* ENCAPSULATED
* TABLE
* NULL
* INHERITED
* UNDETERMINED

States are a transpiler-specific concept. CQL handles data as a variety of data types while SQL variants handle data as either tables or simple values.

The 'ENCAPSULATED' state describes cases where the transpiler will convert a table into a list of tuples so it can be passed around as a single value.

The 'INHERITED' state should always be resolved to one of 'NULL', 'SIMPLE', 'ENCAPSULATED', or 'TABLE' based on the states of its children. If all children have the same state, the 'INHERITED' state should be resolved to that state. Otherwise, 'ENCAPSULATED' takes precedence over 'SIMPLE', 'TABLE' over 'SIMPLE' and 'ENCAPSULATED', and 'NULL' is preceded by every other state.

The 'UNDETERMINED' state is always an error, typically caused by incomplete implementation of state-determining functionality.

# Formats:

* RAW_VALUE
* SINGLE_VALUE_TABLE
* SINGLE_VALUE_TABLE_REFERENCE
* QUERY
* QUERY_REFERENCE
* INHERITED
* UNDETERMINED

Formats describe how a particular operator is presented.

The 'RAW_VALUE' state always describes an operator that resolves to the 'SIMPLE' state

The 'SINGLE_VALUE_TABLE' and 'SINGLE_VALUE_TABLE_REFERENCE' format can describe an item of either 'SIMPLE' or 'ENCAPSULATED' state.

The 'QUERY' and 'QUERY_REFERENCE' formats always describe an item of 'TABLE' state.

The 'INHERITED' format should always be resolved to one of 'RAW_VALUE', 'SINGLE_VALUE_TABLE', or 'QUERY'.

The 'UNDETERMINED' format is always an error, typically caused by incomplete implementation of format-determining functionality.

# Access Types

* DOT_PROPERTY
* INTERVAL
* SELECT_FROM
* INHERITED
* AUTO
* UNDETERMINED

Access types describe how data should be retrieved from a particular operator.

The 'DOT_PROPERTY' access type means that data can be achieved by using a dot property (ex. this.data)

The 'INTERVAL' access type represents a CQL interval stored as a tuple.

The 'SELECT_FROM' access type means that an item needs to be accessed as if it is a query

The 'INHERIT' access type is an access type for some unary operators and resolves automatically to the access type of that operator's child

The 'AUTO' access type is an access type for some unary operators and resolves automatically to either the 'DOT_PROPERTY' or 'SELECT_FROM' access type. If the operator's child has a 'DOT_PROPERTY' access type and also a 'RAW_VALUE', 'SINGLE_VALUE_TABLE_REFERENCE, or 'QUERY_REFERENCE' format, it preferentially resolves to the 'DOT_PROPERTY' access type. Otherwise it resolves to the 'SELECT_FROM' access type.

It is always an error to attempt to access data within an operator of 'UNDETERMINED' access type.