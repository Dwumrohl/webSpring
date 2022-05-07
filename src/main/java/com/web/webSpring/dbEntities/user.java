package com.web.webSpring.dbEntities;

import org.bson.BsonTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.ZoneId;

@Document(collection = "users")
public class user {

    @Id
    private String id;

    @Field("username")
    private String username;

    @Field("password")
    private String password;

    @Field("admin")
    private boolean admin;

    @Field("blocked")
    private boolean blocked;

    @Field("register_date")
    private BsonTimestamp register_date;

    @Field("login_date")
    private BsonTimestamp login_date;

    public user(){

    }

    public user(String username, String password) {
        this.username = username;
        this.password = password;
        this.admin = false;
        this.blocked = false;
    }

    public user(String id, String username, String password, boolean admin, boolean blocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.blocked = blocked;
    }

    public user(String id, String username, String password, boolean admin, boolean blocked, BsonTimestamp register_date, BsonTimestamp login_date) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.blocked = blocked;
        this.register_date = register_date;
        this.login_date = login_date;
    }

    public String getRegister_date() {
        long value=register_date.getValue();
        long unix = value >> 32;
        Instant time = Instant.ofEpochSecond(unix);
        time.atZone(ZoneId.of("Etc/GMT+4"));
        return time.toString();
    }

    public void setRegister_date(BsonTimestamp register_date) {
        this.register_date = register_date;
    }

    public String getLogin_date() {
        long value=login_date.getValue();
        long unix = value >> 32;
        Instant time = Instant.ofEpochSecond(unix);
        time.atZone(ZoneId.of("Etc/GMT+4"));
        return time.toString();
    }

    public void setLogin_date(BsonTimestamp login_date) {
        this.login_date = login_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
