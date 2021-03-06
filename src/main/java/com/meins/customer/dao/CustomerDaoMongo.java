/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meins.customer.dao.CustomerDao#find(org.bson.types.ObjectId)
	 */
	@Override
	public CustomerModel find(ObjectId customerId) {
		return super.findOne(customerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meins.customer.dao.CustomerDao#findAllByPage(int)
	 */
	public List<CustomerModel> findAllByPage(int page) {
		// accessing the first page by a page size of 5
		Page<CustomerModel> customerModelPage = super.findAll(new PageRequest(page, 5));
		return customerModelPage.getContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.mongodb.repository.support.SimpleMongoRepository
	 * #delete(java.lang.Object)
	 */
	@Override
	public void delete(CustomerModel customerModel) {
		super.delete(customerModel);
	}
}
