package gov.va.transpiler.sparksql.node.binary;

import org.hl7.elm.r1.DateTimePrecision;

import gov.va.transpiler.sparksql.node.Binary;

public class DifferenceBetweenNode extends Binary {
    
    private DateTimePrecision dateTimePrecision;

    public DateTimePrecision getDateTimePrecision() {
        return dateTimePrecision;
    }

    public void setDateTimePrecision(DateTimePrecision dateTimePrecision) {
        this.dateTimePrecision = dateTimePrecision;
    }

    @Override
    public String asOneLine() {
        switch (dateTimePrecision) {
            case DAY:
                return "DATEDIFF(" + getChild1().asOneLine() + ", " + getChild2().asOneLine() + ")";
            default:
                break;
        }
        return "Unsupported DifferenceBetween with precision " + dateTimePrecision + " and children [" + getChild1().asOneLine() + "][" + getChild2().asOneLine() + "]";
    }
}
