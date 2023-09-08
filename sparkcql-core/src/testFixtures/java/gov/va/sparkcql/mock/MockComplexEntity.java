package gov.va.sparkcql.mock;

import gov.va.sparkcql.types.QualifiedIdentifier;
import org.cqframework.cql.elm.serializing.jackson.ElmJsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class MockComplexEntity implements Serializable {

    private int id;
    private String value;
    private Instant dateTime;
    private List<QualifiedIdentifier> homogenousList;
    private List<Object> heterogeneousList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public List<QualifiedIdentifier> getHomogenousList() {
        return homogenousList;
    }

    public void setHomogenousList(List<QualifiedIdentifier> homogenousList) {
        this.homogenousList = homogenousList;
    }

    public List<Object> getHeterogeneousList() {
        return heterogeneousList;
    }

    public void setHeterogeneousList(List<Object> heterogeneousList) {
        this.heterogeneousList = heterogeneousList;
    }
//
//    private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
//        var mapper = ElmJsonMapper.getMapper();
//        mapper.registerModule(new JavaTimeModule());
//        var that = mapper.readValue((String)input.readObject(), MockComplexEntity.class);
//        this.id = that.id;
//        this.heterogeneousList = that.heterogeneousList;
//        this.homogenousList = that.homogenousList;
//    }
//
//    private void writeObject(ObjectOutputStream output) throws IOException {
//        var mapper = ElmJsonMapper.getMapper();
//        mapper.registerModule(new JavaTimeModule());
//        var json = mapper.writeValueAsString(this);
//        output.writeObject(json);
//    }
}