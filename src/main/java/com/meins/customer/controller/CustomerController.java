/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.controller;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/customer")
public class CustomerController {

	private static Logger LOGGER = Logger.getLogger(CustomerController.class.getName());

	private DaoConfig daoConfig;

	private CustomerMapper customerMapper;

	@RequestMapping(value = "{customerId}", method = RequestMethod.GET)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public @ResponseBody
	Customer getCustomerById(@PathVariable String customerId) {
		return customerMapper.mapCustomerModelToCustomer(daoConfig.getCustomerDao().find(new ObjectId(customerId)));
	}

	@RequestMapping(value = { "/save" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public @ResponseBody
	String saveCustomer(@RequestBody Customer customer) {
		LOGGER.info("create new customer");
		CustomerModel customerModel = daoConfig.getCustomerDao().save(
				customerMapper.mapCustomerToCustomerModel(customer));
		return customerModel.getId().toString();
	}

	@Autowired
	public void setDaoConfig(DaoConfig daoConfig) {
		this.daoConfig = daoConfig;
	}

	@Autowired
	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}
}