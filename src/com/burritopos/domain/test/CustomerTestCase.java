/**
 * 
 */
package com.burritopos.domain.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import junit.framework.AssertionFailedError;
import com.burritopos.domain.Customer;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
public class CustomerTestCase extends BurritoPOSTestCase {
    @SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(CustomerTestCase.class);

	public CustomerTestCase() {
		super();
	}

	/**
	 * Test method for {@link com.burritopos.domain.Customer#validate()}.
	 */
	@Test
	public void testValidate() throws AssertionFailedError {
		Customer c = new Customer();
		c.setId(new Integer("1"));
		c.setEmailAddress("jim@gmail.com");
		c.setFirstName("Jim");
		c.setLastName("Bloom");

		assertTrue(c.validate());
	}

	/**
	 * Negative Customer Validate Unit Test
	 */
	@Test
	public void testInvalidCustomer() throws AssertionFailedError {
		Customer c = new Customer();

		assertFalse(c.validate());
	}

	/**
	 * Test method for {@link com.burritopos.domain.Customer#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() throws AssertionFailedError {
		Customer c = new Customer(new Integer("1"),"Jim","Bloom","jim@gmail.com");
		Customer d = c;

		assertTrue(c.equals(d));
	}

	/**
	 * Negative Customer Equals Unit Test
	 */
	@Test
	public void testNotEqualsCustomer() throws AssertionFailedError {
		Customer c = new Customer(new Integer("1"),"Jim","Bloom","jim@gmail.com");
		Customer d = new Customer();

		assertFalse(c.equals(d));
	}
}
