/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peter.config;

import org.springframework.context.annotation.Configuration;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author peter
 */
@Configuration
@ComponentScan("com.sportang.mongo.repositories")
@EnableMongoRepositories("com.sportang.mongo.repositories")
@PropertySource("classpath:/properties/system-${spring.profiles.active}.properties")
@Profile({"test","production", "default"})
public class MongoConfigProduction extends AbstractMongoConfiguration {

    @Autowired
    private Environment env;
    
    @Override
    protected String getDatabaseName() {
        return env.getProperty("mongo.database");
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoCredential credential
                = MongoCredential.createCredential(
                        env.getProperty("mongo.username"),
                        env.getProperty("mongo.database"),
                        env.getProperty("mongo.password").toCharArray());
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        MongoClientOptions options=builder.sslEnabled(true).build(); 
        
        return new MongoClient(
                new ServerAddress(env.getProperty("mongo.ip"),Integer.parseInt(env.getProperty("mongo.port"))),//"127.0.0.1", 27017),
                Arrays.asList(credential)
                ,options
        );
    }

}
