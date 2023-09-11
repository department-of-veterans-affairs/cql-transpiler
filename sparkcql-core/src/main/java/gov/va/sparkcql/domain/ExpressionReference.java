package gov.va.sparkcql.domain;

import gov.va.sparkcql.types.QualifiedIdentifier;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;

import java.io.Serializable;

public class ExpressionReference implements Serializable {
    
    private QualifiedIdentifier libraryIdentifier;

    private String expressionDefName;

    public QualifiedIdentifier getLibraryIdentifier() {
        return libraryIdentifier;
    }

    public void setLibraryIdentifier(QualifiedIdentifier libraryIdentifier) {
        this.libraryIdentifier = libraryIdentifier;
    }

    public ExpressionReference withLibraryIdentifier(QualifiedIdentifier libraryIdentifier) {
        this.libraryIdentifier = libraryIdentifier;
        return this;
    }

    public ExpressionReference withLibraryIdentifier(Library library) {
        this.libraryIdentifier = QualifiedIdentifier.from(library.getIdentifier());
        return this;
    }

    public String getExpressionDefName() {
        return expressionDefName;
    }

    public void setExpressionDefName(String name) {
        this.expressionDefName = name;
    }

    public ExpressionReference withExpressionDefName(String expressionDefName) {
        this.expressionDefName = expressionDefName;
        return this;
    }

    public ExpressionReference withExpressionDef(ExpressionDef expressionDef) {
        this.expressionDefName = expressionDef.getName();
        return this;
    }
}