# API Design & Usage
## Setup

### Mount Clinical Data as a Hive Table
```sql
CREATE TABLE FhirDataR4.Encounter
...
CREATE TABLE KnowledgeBase.ValueSet
...
```

### Define a CQL Library
```sql
library Sample version '1'

parameter MeasurementPeriod Interval<DateTime>

define "Encounters":
  ["Encounter": "Inpatient Encounter"] E
    where E.period ends during MeasurementPeriod
```

### Initialize a SparkCQL Session
```java

var translator = new Translator(spark).withConfig("sparkcql.filelibraryadapter.path", "./src/test/resources/cql");
var dfMap = translator.translateLibrary(library, parameters);
var df2 = translator.translateExpressionDef(exprDef);
var df3 = translator.translateExpressionDef("[Condition]");
var df4 = translator.translateFunctionDef(funcDef);

var le = new LibraryEvaluator(ctx, cqlText);
var ee = new ExpressionEvaluator(ctx, "[Condition]");

var el = new ExecutableLibrary(ctx, cqlText);
var el = new ExecutableLibrary(ctx, new LibraryIdentifier().withId("MyLibrary"));
el.withParameter("Measurement Period", new Interval<Date>("", ""));

var el = new ExecutableLibrary(ctx, "define 'Hello': [Encounter]");

var df1 = el.expressionDef("");
var df2 = el.expressionDef("").as(myEncoder);
var df3 = el.functionDef("", "2023-01-01");
var df4 = el.functionDef("", df1);

```