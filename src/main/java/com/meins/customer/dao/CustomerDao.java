/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.dao;

import org.bson.types.ObjectId;

import com.meins.customer.db.model.CustomerModel;

/**
 * 
 * Interface to encapsulate customer data access object calls.
 * 
 * @author mkalsow
 * 
 */
public interface CustomerDao {

	/**
	 * Save or update a customer in mongo database.
	 */
	public CustomerModel save(CustomerModel customer);

	/**
	 * Find a customer in mongo database with unique ObjectId from customer.
	 */
	public CustomerModel find(ObjectId customerId);

}
