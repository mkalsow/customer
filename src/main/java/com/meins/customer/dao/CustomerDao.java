/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.dao;

import java.util.List;

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
	 * 
	 * @param customer
	 *            Customer object which has to be saved.
	 * @return saved customer.
	 * 
	 */
	public CustomerModel save(CustomerModel customer);

	/**
	 * Find a customer in mongo database with unique ObjectId from customer.
	 * 
	 * @param customerId
	 *            unique ObjectId from customer.
	 * @return founded customer.
	 */
	public CustomerModel find(ObjectId customerId);

	/**
	 * Find all customer in mongo database with paging.
	 * 
	 * @param page
	 *            page for paging.
	 * @return List of founded customer.
	 */
	public List<CustomerModel> findAllByPage(int page);

	/**
	 * Delete a customer in mongo database.
	 * 
	 * @param customer
	 *            Customer object which has to be deleted.
	 */
	public void delete(CustomerModel customer);

}
