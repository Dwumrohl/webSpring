package com.web.webSpring.dbEntities;

import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

public class MongoDBOperations {
    public List<user> getAllUsers(MongoOperations mongoOperation) {
        return mongoOperation.findAll(user.class);
    }

    public void addUser(MongoOperations mongoOperation, user usr){
        mongoOperation.save(usr,"users");
    }
}

