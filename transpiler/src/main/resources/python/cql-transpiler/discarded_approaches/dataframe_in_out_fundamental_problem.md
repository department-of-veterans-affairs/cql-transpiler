If we want to support CQL data structures, but we want all intermediate values to be in the form of tables/dataframes, we have to have logic to composite multiple tables together into one table. Composition can happen using either unions or joins, but either approach comes with its own issues.

For tables X, Y, Z
define var: {a: [X], b: [Y], c: [Z]}

**Unions**

basic representation:

Table var
|a.X_property  |b.Y_property  |c.Z_property  |
| val 1 from X |              |              |
| val 2 from X |              |              |
|              | val 1 from Y |              |
|              | val 2 from Y |              |
|              |              | val 1 from Z |
|              |              | val 2 from Z |

Problem number one is that unions result in lots of empty space.

E.g. To retrieve a.x_property, you have to do SELECT a.x_property WHERE a.x_property IS NOT NULL

Problem number two is that nesting levels have to be explicitly included in the table, or lists of lists can't be distinguished from lists.

E.g., [All objects of type T where some condition C1 holds, All objects of type T where some condition C2 holds] and [All objects of type T where either C1 or C2 hold] both result in select statements that return the following table:

|T.property1|T.property2|
|          ?|          ?|
|          ?|          ?|
|          ?|          ?|
|          ?|          ?|

... which can't be operated to, for example, get the 2nd element satisfying C2.

So unions either have to look like this:

|a.X_property  |b.Y_property  |c.Z_property  |index_n0|index_n1|
| val 1 from X |              |              |       0|       0|
| val 2 from X |              |              |       0|       1|
|              | val 1 from Y |              |       1|       0|
|              | val 2 from Y |              |       1|       1|
|              |              | val 1 from Z |       2|       0|
|              |              | val 2 from Z |       2|       1|

Or like this:

|a                                         |b                                         |c                                         |
|[{X_property: val 1}, {X_property: val 2}]|                                          |                                          |
|                                          |[{Y_property: val 1}, {Y_property: val 2}]|                                          |
|                                          |                                          |[{Z_property: val 1}, {Z_property: val 2}]|

rebuttal:
it's okay for us to result in this, and okay to use it as an intermediate step.

|a                                         |b                                         |c                                         |
|[{X_property: val 1}, {X_property: val 2}]|[{Y_property: val 1}, {Y_property: val 2}]|[{Z_property: val 1}, {Z_property: val 2}]|

... so long as we find a way to avoid compressing every single dataframe

**Joins**

basic representation:

|a.Z_property  |b.Z_property  |c.Z_property  |
| val 1 from X | val 1 from Y | val 1 from Z |
| val 1 from X | val 1 from Y | val 2 from Z |
| val 1 from X | val 2 from Y | val 1 from Z |
| val 1 from X | val 2 from Y | val 2 from Z |
| val 2 from X | val 1 from Y | val 1 from Z |
| val 2 from X | val 1 from Y | val 2 from Z |
| val 2 from X | val 2 from Y | val 1 from Z |
| val 2 from X | val 2 from Y | val 2 from Z |

For compositing tables without a clear chain of subordination, joins result in a cartesian product of values and are therefore totally unworkable. Joins only work in the narrow case where there's an explicit many-to-one relationship between tables, such as when applying a patient context to a list of encounters.