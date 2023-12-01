package gov.va.transpiler.sparksql.node.ary;

import javax.xml.namespace.QName;

import org.hl7.elm.r1.TypeSpecifier;

import gov.va.transpiler.sparksql.node.Ary;

// Spark SQL doesn't support type specifiers
public abstract class TypeSpecifierNode extends Ary {

    @Override
    public String asOneLine() {
        QName qname = null;
        if (getCqlNodeEquivalent() instanceof TypeSpecifier) {
            qname = ((TypeSpecifier) getCqlNodeEquivalent()).getResultTypeName();
        }
        return qname == null ? getName() : qname.getLocalPart();
    }
}
