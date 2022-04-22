package com.web.webSpring.dbEntities;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<user, String> {
    public List<user> findAll();
    public user findByUsername(String username);
    public List<user> findByPassword(String password);
}
