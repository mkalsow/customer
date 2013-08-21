/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.meins.customer.db.model.CustomerModel;

/**
 * 
 * Implementation of data access object layer for mongo database.
 * 
 * @author mkalsow
 * 
 */
public class CustomerDaoMongo extends SimpleMongoRepository<CustomerModel, ObjectId> implements CustomerDao {

	/**
	 * Constructor for create MongoRepositoryFactory for calls to mongo
	 * database.
	 */
	public CustomerDaoMongo(MongoRepositoryFactory factory, MongoTemplate template) {
		super(new MongoRepositoryFactory(template).<CustomerModel, ObjectId> getEntityInformation(CustomerModel.class),
				template);
	}

	/**
	 * Find one customer in mongo database with unique objectId from customer.
	 */
	@Override
	public CustomerModel find(ObjectId customerId) {
		return super.findOne(customerId);
	}

}
