"IF" statements never spit out a statement that lets you do
"statement"."property"
In general, there are a few statements that may be returning a "simple" value, but can't be treated as "simple" for the purposes of property/high/low. (incl, "If", "InInterval")
Start/End always behave like they're retrieving a value from a simple node (i.e., property style)
type coercion/checking is inconconsistent... sometimes I check types, sometimes I assume coercion will do its job.