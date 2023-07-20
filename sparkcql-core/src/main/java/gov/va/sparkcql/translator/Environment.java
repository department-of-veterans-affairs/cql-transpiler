package gov.va.sparkcql.translator;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class Environment {

    public Environment() {
    }

    private Context context = Context.Unfiltered;
    private Dataset<Row> contextFrame;
    private String model;
    private Map<String, Dataset<Row>> expressionDefDeclarations = new HashMap<>();
    private Map<String, Dataset<Row>> querySources = new HashMap<>();

    public Dataset<Row> getExpressionDef(String name) {
        return this.expressionDefDeclarations.get(name);
    }

    public void putExpressionDef(String name, Dataset<Row> ds) {
        this.expressionDefDeclarations.put(name, ds);
    }

    public Dataset<Row> getQuerySource(String name) {
        return this.querySources.get(name);
    }

    public void putQuerySource(String name, Dataset<Row> ds) {
        this.querySources.put(name, ds);
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    public String getModel() {
        return this.model;
    }

    public void setContext(Context context) {
        this.context = context;
    }    

    public Context getContext() {
        return this.context;
    }

    public void setContextFrame(Dataset<Row> contextFrame) {
        this.contextFrame = contextFrame;
    }    

    public Dataset<Row> getContextFrame() {
        return this.contextFrame;
    }
}