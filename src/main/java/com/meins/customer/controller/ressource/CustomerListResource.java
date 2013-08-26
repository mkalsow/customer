/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.controller.ressource;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

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
public class CustomerListResource extends ResourceSupport {

	/**
	 * List of {@link CustomerResource}.
	 */
	protected List<CustomerResource> customerResourceList;

	public List<CustomerResource> getCustomerResourceList() {
		return customerResourceList;
	}

	public void setCustomerResourceList(List<CustomerResource> customerResourceList) {
		this.customerResourceList = customerResourceList;
	}

}