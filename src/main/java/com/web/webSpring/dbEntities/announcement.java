package com.web.webSpring.dbEntities;

import org.bson.BsonTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;

@Document(collection = "announcements")
public class announcement {
    @Id
    private String id;

    @Field("header")
    private String header;

    @Field("type")
    private String type;

    @Field("date")
    private BsonTimestamp date;

    @Field("description")
    private String description;

    @Field("body")
    private String body;

    @Field("annTypeId")
    private String annTypeId;

    @Field("image")
    private String image;

    public announcement() {
    }

    public announcement(String header, String type, BsonTimestamp date, String description, String body, String annTypeId, String image) {
        this.header = header;
        this.type = type;
        this.date = date;
        this.description = description;
        this.body = body;
        this.annTypeId = annTypeId;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDate() {
      long value=date.getValue();
      long unix = value >> 32;
      Instant time = Instant.ofEpochSecond(unix);
      time.atZone(ZoneId.of("Etc/GMT+4"));
      return time.toString();
    }
    public BsonTimestamp getRawDate(){
        return date;
    }

    public void setDate(BsonTimestamp date) {
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
