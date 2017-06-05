package com.sentilabs.okko.configuration;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    @Value("${mongodb.users.db.name}")
    private String MONGODB_DB_NAME;

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGODB_DB_NAME);
        return mongoTemplate;
    }

    @Bean
    public MongoClient mongoClient() {
        MongoClient mongoClient = new MongoClient();
        return mongoClient;
    }
}
