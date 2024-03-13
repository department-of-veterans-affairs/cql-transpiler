library Retrievals version '1.0'
using QDM version '5.6'
context Unfiltered
define testLiteral: 1
define testReference: testLiteral
define testEmptyList: {}
define testList: {1, 2}
define testRetrieve: ["Encounter, Performed"]
define testTuple_withCollection: {foo: testRetrieve, bar: 1}
define testTupleReference: testTuple_withCollection.bar
define testUnion: ["Encounter, Performed"] union ["Encounter, Performed"]
define testUnionOfTableAndTupleReference: testRetrieve union testTuple_withCollection.foo
define testNestedTuple: {baz: 1, foobar: testTuple_withCollection}
define testNestedTupleReference: testNestedTuple.foobar.bar
define testNestedTupleReferenceTable: testNestedTuple.foobar.foo
define function testFunction(val Integer):
    2 * val
define testFunctionReference: testFunction(1)
define testConcatenate: '1' + '2'
define testMathOperations: (1 + 2 / -3 + 4) * 5 + testLiteral
define testBoolean: 1 = 2
define testDateTime: @2010-01-02T14:30:07.76
define testQuery_returnColumnValue: ["Encounter, Performed"] E return E.relevantPeriod
define testQuery_withWhere: ["Encounter, Performed"] E where E.relevantPeriod ends after testDateTime
define testQuery_sortBy: ["Encounter, Performed"] E sort by start of relevantPeriod
define testQuery_returnColumnValueProperty: ["Encounter, Performed"] E return E.relevantPeriod.high
context Patient
define testCount_patientContext: Count(["Encounter, Performed"])
context Unfiltered
define testCount_unfilteredContext: Count(["Encounter, Performed"])
