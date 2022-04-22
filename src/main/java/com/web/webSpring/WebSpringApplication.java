package com.web.webSpring;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.web.webSpring.dbEntities.MongoDBOperations;
import com.web.webSpring.dbEntities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;


@SpringBootApplication
public class WebSpringApplication {


	public static void main(String[] args) {
		SpringApplication.run(WebSpringApplication.class, args);

	}


}
