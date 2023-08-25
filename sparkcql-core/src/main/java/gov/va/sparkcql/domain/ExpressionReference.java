package gov.va.sparkcql.domain;

import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;

import java.io.Serializable;

public class ExpressionReference implements Serializable {
    
    private String libraryName;

    private String expressionDefName;

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public ExpressionReference withLibraryName(String libraryName) {
        this.libraryName = libraryName;
        return this;
    }

    public ExpressionReference withLibrary(Library library) {
        this.libraryName = library.getIdentifier().getId();
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