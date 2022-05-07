package com.web.webSpring;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.web.webSpring.dbEntities.MongoDBOperations;
import com.web.webSpring.dbEntities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;


@SpringBootApplication
public class WebSpringApplication {

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofBytes(128000000));
		factory.setMaxRequestSize(DataSize.ofBytes(10000000));
		return factory.createMultipartConfig();
	}

	public static void main(String[] args) {
		SpringApplication.run(WebSpringApplication.class, args);

	}




}
