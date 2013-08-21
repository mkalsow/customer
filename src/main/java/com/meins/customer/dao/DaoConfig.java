/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Service;

import com.meins.customer.db.MongoConfig;

/**
 * 
 * Class for configure data access object layer.
 * 
 * @author mkalsow
 * 
 */
@Configuration
@Import(MongoConfig.class)
@Service
public class DaoConfig {

	/**
	 * Inject MongoConfig class to connect to mongo database.
	 */
	@Autowired
	private MongoConfig mongoConfig;

	/**
	 * Inject MongoRepositoryFactory class to connect to mongo database.
	 */
	@Bean
	public MongoRepositoryFactory getMongoRepositoryFactory() {
		try {
			return new MongoRepositoryFactory(mongoConfig.mongoTemplate());
		} catch (Exception e) {
			throw new RuntimeException("error creating mongo repository factory", e);
		}
	}

	/**
	 * Inject CustomerDao class use data access obejct layer..
	 */
	@Bean
	public CustomerDao getCustomerDao() {
		try {
			return new CustomerDaoMongo(getMongoRepositoryFactory(), mongoConfig.mongoTemplate());
		} catch (Exception e) {
			throw new RuntimeException("Error creating CustomerDao", e);
		}
	}

}
