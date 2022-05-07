package com.web.webSpring.dbEntities;

import org.bson.BsonTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;

@Document(collection = "comments")
public class comment {
    @Id
    private String id;

    @Field("body")
    private String body;

    @Field("date")
    private BsonTimestamp date;

    @Field("annId")
    private String annId;

    @Field("userId")
    private String userId;

    private String userName;

    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public comment(){
    }

    public comment(String body, BsonTimestamp date, String annId, String userId) {
        this.body = body;
        this.date = date;
        this.annId = annId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        long value=date.getValue();
        long unix = value >> 32;
        Instant time = Instant.ofEpochSecond(unix);
        //time.atZone(ZoneId.of("Etc/GMT+4"));
        return time.toString();
    }

    public void setDate(BsonTimestamp date) {
        this.date = date;
    }

    public String getAnnId() {
        return annId;
    }

    public void setAnnId(String annId) {
        this.annId = annId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
