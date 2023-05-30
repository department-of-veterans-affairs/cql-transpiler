library LiteralDefinitions version '1.0'

using FHIR version '4.0.0'
//using QUICK

context Patient

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

define "MyTupleLiteral":
    Tuple {
        Name: 'Patrick',
        DOB: @2014-01-01,
        Address: Tuple { Line1: '41 Spinning Ave', City: 'Dayton', State: 'OH' },
        Phones: { Tuple { Number: '202-413-1234', Use: 'Home' } }
    }

define "MyTupleRef":
    "MyTupleLiteralList"
