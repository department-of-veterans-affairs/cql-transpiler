library ComplexLiteral version '2.1'

valueset "Mock Valueset A": 'urn:oid:1.2.3.4.5.6.789'

parameter "Measurement Period" Interval<DateTime>

define "MyTupleLiteral":
  Tuple {
    Name: 'Patrick',
    DOB: @2014-01-01,
    Address: Tuple { Line1: '41 Spinning Ave', City: 'Dayton', State: 'OH' },
    Phones: { Tuple { Number: '202-413-1234', Use: 'Home' } }
  }

define "MyTupleLiteralList":
  {
    Tuple {
      Name: 'Patrick',
      DOB: @2014-01-01,
      Address: Tuple { Line1: '41 Spinning Ave', City: 'Dayton', State: 'OH' },
      Phones: { Tuple { Number: '202-413-1234', Use: 'Home' } }
    },
    {
      Name: 'Beth',
      DOB: @2014-01-01,
      Address: Tuple { Line1: '123 Main Ave', City: 'Anytown', State: 'GA' },
      Phones: { Tuple { Number: '202-413-1234', Use: 'Home' } }
    }
  }

define "Combined":
  "MyTupleLiteralList" A
  with "MyTupleLiteral" B
    such that A.Name = B.Name

define "MyTupleRef":
  "MyTupleLiteralList"

define "OhNo":
  Tuple {
    Name: 'Patrick',
    //FunList: "MyTupleLiteralList"    // IMPORTANT: CQL allows expressions of any type as tuple values which
    //FunRetrieve: [Encounter]         // breaks our approach to translating to native spark.
  }