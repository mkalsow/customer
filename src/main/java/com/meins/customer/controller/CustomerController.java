/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

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
import com.meins.customer.controller.ressource.CustomerListResource;
import com.meins.customer.controller.ressource.CustomerResource;
import com.meins.customer.controller.ressource.CustomerResourceAssembler;
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

	/**
	 * Search Customer with customerId and get request. Example:
	 * http://localhost:8181/customer/customers/521909f70364f3df7147f613
	 */
	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<CustomerResource> getCustomerById(@PathVariable String customerId) {
		// find customer object with customerId
		Customer customer = customerMapper.mapCustomerModelToCustomer(daoConfig.getCustomerDao().find(
				new ObjectId(customerId)));
		// map to resource object for hypermedia support
		CustomerResource resource = customerResourceAssembler.toResource(customer);
		// add delete link for hypermedia support
		resource.add(linkTo(methodOn(CustomerController.class).deleteCustomer(customer.getCustomerId())).withRel(
				"delete"));
		return new ResponseEntity<CustomerResource>(resource, HttpStatus.OK);
	}

	/**
	 * Search Customer without filter with get request. A maximum of 5 customer
	 * will be returned as a result. The list of customers can be can be paged
	 * with hyperlinks from the response. Example:
	 * http://localhost:8181/customer/customers
	 */
	@RequestMapping(method = RequestMethod.GET)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public @ResponseBody
	ResponseEntity<CustomerListResource> listCustomer() {
		// find customer from page 0
		CustomerListResource customerResource = findCustomerByPage(0);

		return new ResponseEntity<CustomerListResource>(customerResource, HttpStatus.OK);
	}

	/**
	 * Search Customer without filter with get request. A maximum of 5 customer
	 * will be returned as a result. The list of customers can be can be paged
	 * with hyperlinks from the response. Example:
	 * http://localhost:8181/customer/customers
	 */
	@RequestMapping(value = "/page={page}", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<CustomerListResource> listCustomerByPage(@PathVariable int page) {
		// find customer from page
		CustomerListResource customerResource = findCustomerByPage(page);

		return new ResponseEntity<CustomerListResource>(customerResource, HttpStatus.OK);
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

	/**
	 * @param customer
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public @ResponseBody
	ResponseEntity<Void> updateCustomer(@RequestBody Customer customer) {
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

	private CustomerListResource findCustomerByPage(int page) {
		// find all customer from page
		List<CustomerModel> customerModelList = daoConfig.getCustomerDao().findAllByPage(page);

		// add all founded customer to list
		List<CustomerResource> customers = new ArrayList<CustomerResource>();
		for (CustomerModel customerModel : customerModelList) {
			customers
					.add(customerResourceAssembler.toResource(customerMapper.mapCustomerModelToCustomer(customerModel)));

		}

		// create response resource and "next" link
		CustomerListResource customerResource = new CustomerListResource();
		customerResource.add(linkTo(methodOn(CustomerController.class).listCustomerByPage(++page)).withRel("next"));
		customerResource.setCustomerResourceList(customers);
		return customerResource;
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