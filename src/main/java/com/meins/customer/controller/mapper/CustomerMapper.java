/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.controller.mapper;

import org.bson.types.ObjectId;

import com.meins.customer.db.model.CustomerModel;
import com.meins.customer.domain.Customer;

/**
 * 
 * Class for mapping customer object from client to customer database object.
 * 
 * @author mkalsow
 * 
 */
public class CustomerMapper {

	/**
	 * map customer database object to customer client object.
	 */
	public Customer mapCustomerModelToCustomer(CustomerModel customerModel) {
		Customer customer = new Customer();

		customer.setCustomerId(customerModel.getId().toString());
		customer.setUser(customerModel.getUser());
		customer.setPassword(customerModel.getPassword());
		customer.setLastname(customerModel.getLastname());
		customer.setForename(customerModel.getForename());
		customer.setBirthday(customerModel.getBirthday());
		customer.setCountry(customerModel.getCountry());
		customer.setCity(customerModel.getCity());
		customer.setZipcode(customerModel.getZipcode());
		customer.setStreet(customerModel.getStreet());
		customer.setHousenumber(customerModel.getHousenumber());

		return customer;

	}

	/**
	 * map customer object from client to customer database object.
	 */
	public CustomerModel mapCustomerToCustomerModel(Customer customer) {
		CustomerModel customerModel = new CustomerModel();

		customerModel.setId(new ObjectId(customer.getCustomerId()));
		customerModel.setUser(customer.getUser());
		customerModel.setPassword(customer.getPassword());
		customerModel.setLastname(customer.getLastname());
		customerModel.setForename(customer.getForename());
		customerModel.setBirthday(customer.getBirthday());
		customerModel.setCountry(customer.getCountry());
		customerModel.setCity(customer.getCity());
		customerModel.setZipcode(customer.getZipcode());
		customerModel.setStreet(customer.getStreet());
		customerModel.setHousenumber(customer.getHousenumber());

		return customerModel;
	}
}
