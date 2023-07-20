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
```scala
val sampleLibrary = scala.io.Source.fromFile("sample.cql").mkString

val encounterBinding = Binding(
  BindClinicalType("http://hl7.org/fhir", "Encounter", "4"),
  BindSparkTableSource("FhirDataR4", "Encounter"))

val valueSetBinding = Binding(
  BindValueSetType(),
  BindSparkTableSource("KnowledgeBase", "ValueSet"))

val sparkcql = SparkCqlSession.build(spark)
  .withBinding(encounterBinding)
  .withBinding(valueSetBinding)
  .withLibrary(sampleLibrary)
```

## Primary Scenarios
### Evaluate CQL
```scala
val dm = spark.cql("""
  parameter MeasurementPeriod default Interval[@2023-01-01, @2024-01-01)

  include Sample version '1'
  
  define "Encounters over 120 Days":
    "Encounters" E
      where duration in days of E.period >= 120
  """)

dm("Encounters over 120 Days").show()
dm("Encounters", "Sample 1").show()
```

### Evaluate Using CQL Operator with Typed Parameters
```scala
val dm = spark.cql(
  "include Sample version '1'",
  Map("MeasurementPeriod", Interval("2023-01-01", "2024-01-01")))
```

### Enumerate Defined Data
```scala
dm.foreach(println(_.define.library + _.define.name))
dm.foreach(_.data.show())
```

## Alternate Scenarios
### Bind a CQL Library
```sql
CREATE TABLE KnowledgeBase.Library
...
```

```scala
val libraryBinding = Binding(
  BindLibraryType(),
  BindSparkTableSource("KnowledgeBase", "Library"))

val sparkcql = SparkCqlSession.build(spark)
  .withBinding(libraryBinding)
  ...
```

### Use a Bindings Configuration File
```scala
val bindingsConfiguration = scala.io.Source.fromFile("sample.cql").mkString

val sparkcql = SparkCqlSession.build(spark)
  .withBinding(bindingsConfiguration)
...
```
*SparkCQL will search and automatically apply *
## Questions
- Is a bindings config file preferred over a class or resource scanner? Need a no-code option.