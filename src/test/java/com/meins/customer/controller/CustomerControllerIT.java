package com.meins.customer.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.meins.customer.controller.ressource.CustomerResource;
import com.meins.customer.domain.Customer;

public class CustomerControllerIT {

	private static final String BASE_URI = "http://localhost:8181/customer";

	@Test
	public void createCustomer() {
		RestTemplate restTemplate = new RestTemplate();

		// creating new customer
		Customer newCustomer = new Customer();
		newCustomer.setCountry("germany");

		ResponseEntity<Void> response = restTemplate.postForEntity(BASE_URI + "/customers", newCustomer, Void.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// retrieving the newly created customer details using the URI received
		// in Location header
		CustomerResource customerResource = restTemplate.getForObject(response.getHeaders().getLocation(),
				CustomerResource.class);
		assertEquals(newCustomer.getCountry(), customerResource.getCustomer().getCountry());
		assertTrue(customerResource.getCustomer().getCustomerId() != null);

		// delete the customer using the link with rel delete
		Link customerDeleteLink = customerResource.getLink("delete");
		restTemplate.getForObject(customerDeleteLink.getHref(), CustomerResource.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}