package gov.va.sparkcql.repository;

public class MutableFileRepositoryConfiguration implements FileRepositoryConfiguration {

    public MutableFileRepositoryConfiguration(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    private String path;

    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }    
}