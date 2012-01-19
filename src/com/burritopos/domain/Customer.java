/**
 * 
 */
package com.burritopos.domain;

import java.io.Serializable;
//import java.util.UUID;
//import org.apache.log4j.*;
//import java.util.Date;

/**
 * @author james.bloom
 * This class represents a Customer of the restaurant.
 */
public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7324692078379190024L;
    //private static Logger dLog = Logger.getLogger(Customer.class);
	protected Integer id;
	protected String firstName;
	protected String lastName;
	protected String emailAddress;
	
	/**
	 * default constructor
	 */
	public Customer() {
		super();
		//not initializing these variables to correct JUnit Tests
		//this.firstName = "";
		//this.lastName = "";
		//this.emailAddress = "";		
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 */
	public Customer(Integer id, String firstName, String lastName, String emailAddress) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
	}

	/**
	 * validates the object
	 * @return success or failure
	 */
	public boolean validate() {
		if(this.id != null && this.firstName != null && this.lastName != null && this.emailAddress != null)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the objects are equal
	 * @return success or failure
	 */
	@Override 
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		
		if(obj == null || getClass() != obj.getClass())
			return false;
		
	    final Customer other = (Customer) obj;
	    if (this.id != other.id || this.firstName != other.firstName || this.lastName != other.lastName || this.emailAddress != other.emailAddress)
	    	return false;
	    
	    return true;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
}
