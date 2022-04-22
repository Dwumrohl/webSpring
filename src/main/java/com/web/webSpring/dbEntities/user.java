package com.web.webSpring.dbEntities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Override
    public String toString() {
        return String.format(
                "user[id=%s, username='%s', password='%s', admin='%s', blocked='%s' ]",
                id, username, password, admin, blocked);
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
