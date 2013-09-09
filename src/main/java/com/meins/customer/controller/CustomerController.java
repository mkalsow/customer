/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.meins.customer.controller.error.ValidationError;
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

	/**
	 * Logger instance.
	 */
	private static Logger LOGGER = Logger.getLogger(CustomerController.class.getName());

	/**
	 * Factory for dao layer.
	 */
	private DaoConfig daoConfig;

	/**
	 * Customer related mapper methods.
	 */
	private CustomerMapper customerMapper;

	/**
	 * Class for mapping from an entity to a resource type and adding respective
	 * links (hypermedia)
	 */
	private CustomerResourceAssembler customerResourceAssembler;

	/**
	 * The message source is used to fetch localized error message for
	 * validation errors.
	 */
	private MessageSource messageSource;

	@Autowired
	public CustomerController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Search customer with customerId and get request. Example:
	 * http://localhost:8181/customer/customers/521909f70364f3df7147f613
	 * 
	 * @param customerId
	 *            Unique id from customer.
	 * @return customer entity.
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
	 * 
	 * @return List of customer entities from page 0.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<CustomerListResource> listCustomer() {
		// find customer from page 0
		CustomerListResource customerResource = findCustomerByPage(0);

		return new ResponseEntity<CustomerListResource>(customerResource, HttpStatus.OK);
	}

	/**
	 * Search Customer via page filter with get request. A maximum of 5 customer
	 * will be returned as a result. The list of customers can be can be paged
	 * with hyperlinks from the response. Example:
	 * http://localhost:8181/customer/customers/page=2
	 * 
	 * @param page
	 *            Page from result.
	 * @return List of customer entities from page 0.
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<CustomerListResource> listCustomerByPage(@RequestParam int page) {
		// find customer from page
		CustomerListResource customerResource = findCustomerByPage(page);

		return new ResponseEntity<CustomerListResource>(customerResource, HttpStatus.OK);
	}

	/**
	 * Save a new Customer in database with post-request.
	 * 
	 * @param customer
	 *            New customer.
	 * @return HTTP-Header with hyperlink to new created customer.
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public @ResponseBody
	ResponseEntity<Void> saveCustomer(@Valid @RequestBody Customer customer) {
		LOGGER.info("create new customer");
		CustomerModel customerModel = daoConfig.getCustomerDao().save(
				customerMapper.mapCustomerToCustomerModel(customer));
		// create new header with link to created customer
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(methodOn(getClass()).getCustomerById(customerModel.getId().toString())).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/**
	 * Update new Customer in database with put-request.
	 * 
	 * @param customer
	 *            Updated customer.
	 * @return HTTP-Header with hyperlink to updated customer.
	 * 
	 */
	@RequestMapping(method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public @ResponseBody
	ResponseEntity<Void> updateCustomer(@RequestBody Customer customer) {
		LOGGER.info("update customer");
		CustomerModel customerModel = daoConfig.getCustomerDao().save(
				customerMapper.mapCustomerToCustomerModel(customer));
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(methodOn(getClass()).getCustomerById(customerModel.getId().toString())).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/**
	 * Delete a customer with unique customer id an delete-request.
	 * 
	 * @param customerId
	 *            unique customer id.
	 * @return
	 */
	@RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
	public @ResponseBody
	ResponseEntity<HttpStatus> deleteCustomer(@PathVariable String customerId) {
		LOGGER.info("delete customer");
		// find customer object with customerId
		Customer customer = customerMapper.mapCustomerModelToCustomer(daoConfig.getCustomerDao().find(
				new ObjectId(customerId)));
		// delete customer
		daoConfig.getCustomerDao().delete(customerMapper.mapCustomerToCustomerModel(customer));
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	/**
	 * Method to find customer in database and the page the results.
	 * 
	 * @param page
	 *            page you are looking for.
	 * 
	 * @return List of customer from page.
	 */
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

	/**
	 * Method for all calls with wrong parameters.
	 * 
	 * @param ex
	 *            Exception to be thrown when validation on an argument fails.
	 *            {@link MethodArgumentNotValidException}
	 * @return {@link HttpStatus.BAD_REQUEST}
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationError processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		return processFieldErrors(fieldErrors);
	}

	private ValidationError processFieldErrors(List<FieldError> fieldErrors) {
		ValidationError validationError = new ValidationError();

		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
			validationError.addFieldError(fieldError.getField(), localizedErrorMessage);
		}

		return validationError;
	}

	private String resolveLocalizedErrorMessage(FieldError fieldError) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

		// If the message was not found, return the most accurate field error
		// code instead.
		// You can remove this check if you prefer to get the default error
		// message.
		if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
			String[] fieldErrorCodes = fieldError.getCodes();
			localizedErrorMessage = fieldErrorCodes[0];
		}

		return localizedErrorMessage;
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