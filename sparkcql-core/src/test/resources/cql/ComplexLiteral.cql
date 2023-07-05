library ComplexLiteral version '2.1'

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
      ID: 1,
      Name: 'Patrick',
      DOB: @2014-01-01,
      Address: Tuple { Line1: '41 Spinning Ave', City: 'Dayton', State: 'OH' },
      Phones: { Tuple { Number: '202-413-1234', Use: 'Home' } }
    },
    {
      ID: 2,
      Name: 'Beth',
      DOB: @2014-01-01,
      Address: Tuple { Line1: '123 Main Ave', City: 'Anytown', State: 'GA' },
      Phones: { Tuple { Number: '202-413-1234', Use: 'Home' } }
    },
    {
      ID: 1,
      Name: 'Patrick',
      DOB: @2014-01-01,
      Address: Tuple { Line1: '123 Main Ave', City: 'Anytown', State: 'GA' },
      Phones: { Tuple { Number: '202-413-1234', Use: 'Home' } }
    }    
  }

define "JoinedTuples":
  "MyTupleLiteralList" A
  with "MyTupleLiteral" B
    such that A.ID = B.ID

define "NestedTuples":
  Tuple {
    Description: 'Demonstrates nesting',
    NestedLiteral: "MyTupleLiteral",
    NestedList: "MyTupleLiteralList"
  }