package com.web.webSpring.dbEntities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;

@Document(collection = "comments")
public class comment {
    @Id
    private String id;

    @Field("body")
    private String body;

    @Field("date")
    private Timestamp date;

    @Field("annId")
    private String annId;

    @Field("userId")
    private String userId;

    comment(){
    }

    public comment(String body, Timestamp date, String annId, String userId) {
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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
