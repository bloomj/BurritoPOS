/**
 * 
 */
package com.burritopos.domain.test;

//import java.util.UUID;
//import org.apache.log4j.*;
//import java.util.Date;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import com.burritopos.domain.Customer;

/**
 * @author james.bloom
 *
 */
public class CustomerTestCase extends TestCase {
    //private static Logger dLog = Logger.getLogger(CustomerTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.burritopos.domain.Customer#validate()}.
	 */
	public void testValidate() throws AssertionFailedError {
		try {
			Customer c = new Customer();
			c.setId(new Integer("1"));
			c.setEmailAddress("jim@gmail.com");
			c.setFirstName("Jim");
			c.setLastName("Bloom");
			
			assertTrue(c.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testValidCustomer: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Negative Customer Validate Unit Test
	 */
	public void testInvalidCustomer() throws AssertionFailedError {
		try {
			Customer c = new Customer();
			
			assertFalse(c.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testInvalidCustomer: " + e.getMessage());
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link com.burritopos.domain.Customer#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() throws AssertionFailedError {
		try {
			Customer c = new Customer(new Integer("1"),"Jim","Bloom","jim@gmail.com");
			Customer d = c;
			
			assertTrue(c.equals(d));
		}
		catch(Exception e) {
			System.out.println("Exception in testEqualsCustomer: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Negative Customer Equals Unit Test
	 */
	public void testNotEqualsCustomer() throws AssertionFailedError {
		try {
			Customer c = new Customer(new Integer("1"),"Jim","Bloom","jim@gmail.com");
			Customer d = new Customer();
			
			assertFalse(c.equals(d));
		}
		catch(Exception e) {
			System.out.println("Exception in testNotEqualsCustomer: " + e.getMessage());
			fail(e.getMessage());
		}
	}
}
