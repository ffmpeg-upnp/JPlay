package com.mongodb.dao.impl;

import com.mongodb.dao.userBehaviorDAO;
import com.mongodb.pojo.userBehavior;
import com.pojo.User;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by Admin on 2016/7/15.
 */
public class userBehaviorDAOImpl implements userBehaviorDAO {
    private SessionFactory sessionFactory;

    @Override
    public List<userBehavior> findAllBehaviors() {
        ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext-mongodb.xml");
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");
        return mongoOperations.findAll(userBehavior.class);
    }

    @Override
    public List<userBehavior> findBehaviorsByUserId(int userId) {
        ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext-mongodb.xml");
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoOperations.find(query, userBehavior.class);
    }

    @Override
    public List<userBehavior> findBehaviorByVideoId(int videoId) {
        ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext-mongodb.xml");
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");
        Query query = new Query(Criteria.where("videoId").is(videoId));
        return mongoOperations.find(query, userBehavior.class);
    }

    @Override
    public void createBehavior(userBehavior userBehavior) {
        ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext-mongodb.xml");
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");
        mongoOperations.insert(userBehavior);
    }

    @Override
    public void deleteBehaviorsByUserId(int userId) {
        ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext-mongodb.xml");
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");
        Query query = new Query(Criteria.where("userId").is(userId));
        mongoOperations.findAllAndRemove(query, userBehavior.class);
    }

    @Override
    public void deleteBehaviorsByVideoId(int videoId) {
        ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext-mongodb.xml");
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");
        Query query = new Query(Criteria.where("videoId").is(videoId));
        mongoOperations.findAllAndRemove(query, userBehavior.class);
    }

    @Override
    public void deleteBehavior(int userId, int videoId) {
        ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext-mongodb.xml");
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");
        Query query = new Query(Criteria.where("userId").is(userId).and("videoId").is(videoId));
        mongoOperations.findAndRemove(query, userBehavior.class);
    }

    @Override
    public void updateBehavior(int userId, int videoId, int rating) {
        ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext-mongodb.xml");
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");
        Query query = new Query(Criteria.where("userId").is(userId).and("videoId").is(videoId));
        Update update = new Update().set("rating", rating);
        mongoOperations.findAndModify(query, update, userBehavior.class);
    }

    @Override
    public void increaseRating(int userId, int videoId, int inc) {
        ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext-mongodb.xml");
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");
        Query query = new Query(Criteria.where("userId").is(userId).and("videoId").is(videoId));
        Update update = new Update().inc("rating", inc);
        mongoOperations.upsert(query, update, userBehavior.class);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
