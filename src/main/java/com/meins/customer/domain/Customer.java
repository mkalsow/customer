/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.domain;

import java.util.Calendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Customer object from client..
 * 
 * @author mkalsow
 * 
 */
@Document(collection = "customer")
public class Customer {

	/**
	 * customer number.
	 */
	@Id
	private String customerId;

	/**
	 * unique username from customer.
	 */
	@NotEmpty
	@Length(max = 140)
	private String user;

	/**
	 * password from customer.
	 */
	@NotEmpty
	@Length(max = 140)
	private String password;

	/**
	 * lastname from customer.
	 */
	private String lastname;

	/**
	 * forename from customer.
	 */
	private String forename;

	/**
	 * birthday from customer.
	 */
	private Calendar birthday;

	/**
	 * country from customer.
	 */
	private String country;

	/**
	 * city from customer.
	 */
	private String city;

	/**
	 * zipcode from customer.
	 */
	private String zipcode;

	/**
	 * street from customer.
	 */
	private String street;

	/**
	 * housenumber from customer.
	 */
	private String housenumber;

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return this.customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return this.user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return this.lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the forename
	 */
	public String getForename() {
		return this.forename;
	}

	/**
	 * @param forename
	 *            the forename to set
	 */
	public void setForename(String forename) {
		this.forename = forename;
	}

	/**
	 * @return the birthday
	 */
	public Calendar getBirthday() {
		return this.birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return this.country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return this.zipcode;
	}

	/**
	 * @param zipcode
	 *            the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return this.street;
	}

	/**
	 * @param street
	 *            the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the housenumber
	 */
	public String getHousenumber() {
		return housenumber;
	}

	/**
	 * @param housenumber
	 *            the housenumber to set
	 */
	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(customerId).append(user).append(password).toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Customer) {
			final Customer other = (Customer) obj;
			return new EqualsBuilder().append(customerId, other.customerId).append(user, other.user)
					.append(password, other.password).isEquals();
		} else {
			return false;
		}
	}
}