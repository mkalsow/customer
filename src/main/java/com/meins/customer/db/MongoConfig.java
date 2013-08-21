/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.db;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.stereotype.Service;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * 
 * Configuration for mongo db. Class reads mongo.properties from classpath with
 * config.
 * 
 * @author mkalsow
 * 
 */
@Configuration
@Service
@PropertySource("classpath:/mongo.properties")
public class MongoConfig extends AbstractMongoConfiguration {

	/**
	 * Spring environment.
	 */
	private Environment env;

	/**
	 * set the spring environment.
	 */
	@Autowired
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	/**
	 * creates mongo instance with host from spring environment.
	 */
	@Bean
	public Mongo mongo() throws UnknownHostException, MongoException {
		return new Mongo(env.getProperty("host"));
	}

	/**
	 * read database name from spring environment.
	 */
	@Override
	public String getDatabaseName() {
		return env.getProperty("databaseName");
	}
}
