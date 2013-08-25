/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meins.customer.controller.mapper.CustomerMapper;
import com.meins.customer.dao.DaoConfig;
import com.meins.customer.db.model.CustomerModel;
import com.meins.customer.domain.Customer;

/**
 * 
 * Controller for all customer related operations.
 * 
 * @author mkalsow
 * 
 */
@Controller
@RequestMapping(value = "/customers")
public class CustomerController {

	private static Logger LOGGER = Logger.getLogger(CustomerController.class.getName());

	private DaoConfig daoConfig;

	private CustomerMapper customerMapper;

	private CustomerResourceAssembler customerResourceAssembler;

	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<CustomerResource> getCustomerById(@PathVariable String customerId) {
		Customer customer = customerMapper.mapCustomerModelToCustomer(daoConfig.getCustomerDao().find(
				new ObjectId(customerId)));
		CustomerResource resource = customerResourceAssembler.toResource(customer);
		resource.add(linkTo(methodOn(CustomerController.class).deleteCustomer(customer.getCustomerId())).withRel(
				"delete"));
		return new ResponseEntity<CustomerResource>(resource, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseEntity<CustomerResource> listCustomer() {
		Customer customer = new Customer();
		customer.setCountry("blub");
		CustomerResource resource = customerResourceAssembler.toResource(customer);
		resource.add(linkTo(methodOn(CustomerController.class).deleteCustomer(customer.getCustomerId()))
				.withRel("next"));
		return new ResponseEntity<CustomerResource>(resource, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public @ResponseBody
	ResponseEntity<Void> saveCustomer(@RequestBody Customer customer) {
		LOGGER.info("create new customer");
		CustomerModel customerModel = daoConfig.getCustomerDao().save(
				customerMapper.mapCustomerToCustomerModel(customer));
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(methodOn(getClass()).getCustomerById(customerModel.getId().toString())).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public @ResponseBody
	ResponseEntity<CustomerResource> deleteCustomer(@PathVariable String customerId) {
		LOGGER.info("delete  customer");
		// TODO
		// Implementation
		Customer customer = customerMapper.mapCustomerModelToCustomer(daoConfig.getCustomerDao().find(
				new ObjectId(customerId)));
		CustomerResource resource = customerResourceAssembler.toResource(customer);
		return new ResponseEntity<CustomerResource>(resource, HttpStatus.OK);
	}

	@Autowired
	public void setDaoConfig(DaoConfig daoConfig) {
		this.daoConfig = daoConfig;
	}

	@Autowired
	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	@Autowired
	public void setCustomerResourceAssembler(CustomerResourceAssembler customerResourceAssembler) {
		this.customerResourceAssembler = customerResourceAssembler;
	}

}