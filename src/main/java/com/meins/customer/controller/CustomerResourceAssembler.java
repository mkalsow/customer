/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.meins.customer.domain.Customer;

/**
 * 
 * Spring Hateoas provides a ResourceAssemblerSupport base class that helps
 * reducing the amount of code needed to be written for mapping from an entity
 * to a resource type and adding respective links. The assembler can then be
 * used to either assemble a single resource or an Iterable of them. You can see
 * below the resource assembler for customer resource.
 * 
 * @author mkalsow
 * 
 */
@Component
public class CustomerResourceAssembler extends ResourceAssemblerSupport<Customer, CustomerResource> {

	/**
	 * Default constructor.
	 */
	public CustomerResourceAssembler() {
		super(CustomerController.class, CustomerResource.class);
	}

	/**
	 * Method to map {@link Customer}. object to customer
	 * {@link CustomerResource}.
	 */
	public CustomerResource toResource(Customer customer) {
		CustomerResource resource = instantiateResource(customer);
		resource.customer = customer;
		resource.add(linkTo(CustomerController.class).slash(customer.getCustomerId()).withSelfRel());
		return resource;
	}

}