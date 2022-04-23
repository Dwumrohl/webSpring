package com.web.webSpring.dbEntities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;

@Document(collection = "announcements")
public class announcement {
    @Id
    private String id;

    @Field("header")
    private String header;

    @Field("type")
    private String type;

    @Field("date")
    private Timestamp date;

    @Field("description")
    private String description;

    @Field("body")
    private String body;

    @Field("annTypeId")
    private String annTypeId;

    public announcement() {
    }

    public announcement(String header, String type, Timestamp date, String description, String body, String annTypeId) {
        this.header = header;
        this.type = type;
        this.date = date;
        this.description = description;
        this.body = body;
        this.annTypeId = annTypeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAnnTypeId() {
        return annTypeId;
    }

    public void setAnnTypeId(String annTypeId) {
        this.annTypeId = annTypeId;
    }
}
