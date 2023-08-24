package gov.va.sparkcql.domain;

import java.io.Serializable;

public class ExpressionReference implements Serializable {
    
    private String libraryName;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpressionReference withName(String name) {
        this.name = name;
        return this;
    }
}
