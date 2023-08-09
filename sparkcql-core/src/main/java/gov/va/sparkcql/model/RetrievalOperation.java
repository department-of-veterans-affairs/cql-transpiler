package gov.va.sparkcql.model;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hl7.elm.r1.Retrieve;

public class RetrievalOperation {
    
    private Retrieve retrieve;

    public String generateHash() {
        try {
            var uniqueKey = retrieve.getDataType().toString();
            var bytesOfMessage = uniqueKey.getBytes("UTF-8");
            var md = MessageDigest.getInstance("MD5");
            var theMD5digest = md.digest(bytesOfMessage);
            return new String(theMD5digest, StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Retrieve getRetrieve() {
        return retrieve;
    }

    public void setRetrieve(Retrieve retrieve) {
        this.retrieve = retrieve;
    }

    public RetrievalOperation withRetrieve(Retrieve retrieve) {
        this.retrieve = retrieve;
        return this;
    }
}