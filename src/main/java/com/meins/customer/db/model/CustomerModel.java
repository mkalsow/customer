/*
 * Copyright 2013 Martin Kalsow.
 */
package com.meins.customer.db.model;

import java.util.Calendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Customer object to save in database.
 * 
 * @author mkalsow
 * 
 */
@Document(collection = "customer")
public class CustomerModel {

	/**
	 * MongoDb primary key.
	 */
	@Id
	private ObjectId id;

	/**
	 * Unique username from customer.
	 */
	private String user;

	/**
	 * Password from customer. Password has to be "salt + hash".
	 */
	private String password;

	/**
	 * Lastname from customer.
	 */
	private String lastname;

	/**
	 * Forename from customer.
	 */
	private String forename;

	/**
	 * Birthday from customer.
	 */
	private Calendar birthday;

	/**
	 * Country from customer.
	 */
	private String country;

	/**
	 * City from customer.
	 */
	private String city;

	/**
	 * Zipcode from customer.
	 */
	private String zipcode;

	/**
	 * Street from customer.
	 */
	private String street;

	/**
	 * Housenumber from customer.
	 */
	private String housenumber;

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
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
		return new HashCodeBuilder().append(id).append(user).append(password).toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof CustomerModel) {
			final CustomerModel other = (CustomerModel) obj;
			return new EqualsBuilder().append(id, other.id).append(user, other.user).append(password, other.password)
					.isEquals();
		} else {
			return false;
		}
	}
}