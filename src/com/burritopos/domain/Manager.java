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
 * This class represents a Manager of the restaurant.
 */
public class Manager extends Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7526471155622776147L;
    //private static Logger dLog = Logger.getLogger(Manager.class);
	protected String firstName;
	protected String lastName;
	protected Integer employeeID;
    protected Boolean isManager;

	/**
	 * default constructor
	 */
	public Manager() {
		super();
		//not initializing these variables to correct JUnit Tests
		//this.firstName = "";
		//this.lastName = "";
		//this.employeeID = UUID.randomUUID();
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param employeeID
	 */
	public Manager(String firstName, String lastName, Integer employeeID) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeID = employeeID;
	}

	/**
	 * validates the object
	 * @return success or failure
	 */
        @Override
	public boolean validate() {
		if(this.firstName != null && this.lastName != null && this.employeeID != null)
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
		
	    final Manager other = (Manager) obj;
	    if (this.firstName != other.firstName || this.lastName != other.lastName || this.employeeID != other.employeeID)
	    	return false;
	    
	    return true;
	}
	
	/**
	 * @return the firstName
	 */
        @Override
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
        @Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
        @Override
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
        @Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the employeeID
	 */
        @Override
	public Integer getEmployeeID() {
		return employeeID;
	}

	/**
	 * @param employeeID the employeeID to set
	 */
        @Override
	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}

                /**
         *
         * @return
         */
        @Override
        public Boolean getIsManager() {
            return isManager;
        }

        /**
         *
         * @param isManager
         */
        @Override
        public void setIsManager(Boolean isManager) {
            this.isManager = isManager;
        }
}
