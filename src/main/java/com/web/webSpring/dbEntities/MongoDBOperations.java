package com.web.webSpring.dbEntities;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class MongoDBOperations {
    public List<user> getAllUsers(MongoOperations mongoOperation) {
        return mongoOperation.findAll(user.class);
    }

    public List<announcement> getAllAnnouncements(MongoOperations mongoOperations){
        return mongoOperations.findAll(announcement.class);
    }

    public List<announcement> getAllAnnLimit(MongoOperations mongoOperations){
        Query searchAnn = new Query();
        searchAnn.limit(5);
        searchAnn.with(Sort.by(Sort.Direction.DESC, "date"));
        return mongoOperations.find(searchAnn,announcement.class,"announcements");
    }

    public List<annType> getAllAnnTypes(MongoOperations mongoOperations){
        return mongoOperations.findAll(annType.class);
    }

    public List<comment> getAllComments(MongoOperations mongoOperations){
        return mongoOperations.findAll(comment.class);
    }

    public void addUser(MongoOperations mongoOperation, user usr){
        mongoOperation.save(usr,"users");
    }

    public void addAnnouncement(MongoOperations mongoOperation, announcement ann){
        mongoOperation.save(ann,"announcements");
    }

    public void addAnnType(MongoOperations mongoOperation, annType ant){
        mongoOperation.save(ant,"announcementTypes");
    }

    public void addComment(MongoOperations mongoOperation, comment comm){
        mongoOperation.save(comm,"comments");
    }

    public user searchUser(MongoOperations mongoOperation, String criteria, String value) {
        Query searchUser = new Query(Criteria.where(criteria).is(value));
        return mongoOperation.findOne(searchUser, user.class);
    }

    public List<user> searchUsers(MongoOperations mongoOperation, String criteria, String value) {
        Query searchUser = new Query(Criteria.where(criteria).is(value));
        return mongoOperation.find(searchUser,user.class,"users");
    }

    public announcement searchAnn(MongoOperations mongoOperation, String criteria, String value) {
        Query searchAnn = new Query(Criteria.where(criteria).is(value));
        return mongoOperation.findOne(searchAnn, announcement.class);
    }

    public List<announcement> searchAnnS(MongoOperations mongoOperation, String criteria, String value) {
        Query searchAnn = new Query(Criteria.where(criteria).is(value));
        return mongoOperation.find(searchAnn,announcement.class,"announcements");
    }

    public annType searchAnnType(MongoOperations mongoOperation, String criteria, String value) {
        Query searchAnnT = new Query(Criteria.where(criteria).is(value));
        return mongoOperation.findOne(searchAnnT, annType.class);
    }

    public List<annType> searchAnnTypes(MongoOperations mongoOperation, String criteria, String value) {
        Query searchAnnT = new Query(Criteria.where(criteria).is(value));
        return mongoOperation.find(searchAnnT,annType.class,"announcementTypes");
    }

    public comment searchComment(MongoOperations mongoOperation, String criteria, String value) {
        Query searchComm = new Query(Criteria.where(criteria).is(value));
        return mongoOperation.findOne(searchComm, comment.class);
    }

    public List<comment> searchComments(MongoOperations mongoOperation, String criteria, String value) {
        Query searchComm = new Query(Criteria.where(criteria).is(value));
        return mongoOperation.find(searchComm,comment.class,"comments");
    }

    public void updateUser(MongoOperations mongoOperation, String criteria, String value, String updateCriteria, String updateValue) {
        Query searchUser = new Query(Criteria.where(criteria).is(value));
        mongoOperation.updateMulti(searchUser, Update.update(updateCriteria, updateValue), user.class);
    }

    public void updateAnnouncement(MongoOperations mongoOperation, String criteria, String value, String updateCriteria, String updateValue) {
        Query searchAnn = new Query(Criteria.where(criteria).is(value));
        mongoOperation.updateFirst(searchAnn, Update.update(updateCriteria, updateValue), announcement.class);
    }

    public void updateComment(MongoOperations mongoOperation, String criteria, String value, String updateCriteria, String updateValue) {
        Query searchComm = new Query(Criteria.where(criteria).is(value));
        mongoOperation.updateFirst(searchComm, Update.update(updateCriteria, updateValue), comment.class);
    }

    public void removeUser(MongoOperations mongoOperation, String criteria,String value) {
        Query searchUser = new Query(Criteria.where(criteria).is(value));
        mongoOperation.remove(searchUser, user.class);
    }

    public void removeAnn(MongoOperations mongoOperation, String criteria,String value) {
        Query searchAnn = new Query(Criteria.where(criteria).is(value));
        mongoOperation.remove(searchAnn, announcement.class);
    }

    public void removeComment(MongoOperations mongoOperation, String criteria,String value) {
        Query searchComm = new Query(Criteria.where(criteria).is(value));
        mongoOperation.remove(searchComm, comment.class);
    }
}

