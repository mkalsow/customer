/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.controller.ressource;

import org.springframework.hateoas.ResourceSupport;

import com.meins.customer.domain.Customer;

/**
 * 
 * All the rest operations for customer will create a CustomerResource for
 * returning to the client. This class inheriting resource's classes from
 * ResourceSupport. As result you get the support for adding Link(s) to the
 * resources for hypermedia REST API.
 * 
 * @author mkalsow
 * 
 */
public class CustomerResource extends ResourceSupport {

	/**
	 * {@link Customer}.
	 */
	protected Customer customer;

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}