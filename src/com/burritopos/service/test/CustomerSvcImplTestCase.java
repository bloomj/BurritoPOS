/**
 * 
 */
package com.burritopos.service.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.burritopos.domain.Customer;

import junit.framework.AssertionFailedError;
import com.burritopos.service.dao.ICustomerSvc;
import com.burritopos.test.BurritoPOSTestCase;

/**
 * @author james.bloom
 *
 */
public class CustomerSvcImplTestCase extends BurritoPOSTestCase {
	private Customer c;
	@Autowired
	private ICustomerSvc ics;
	@SuppressWarnings("unused")
	private static Logger dLog = Logger.getLogger(CustomerSvcImplTestCase.class);

	public CustomerSvcImplTestCase() {
		super();
	}
	
	/**
	 * Sets up the necessary code to run the tests.
	 *
	 * @throws Exception if it cannot set up the test.
	 */
	@Before
	public void initCommonResources() throws Exception {
		super.initCommonResources();
		
		c = new Customer(new Integer("1"),"Jim","Bloom","jim@gmail.com");
	}

	/**
	 * Unit Tests for Customer service
	 * @throws Exception 
	 */
	@Test
	public void testCustomerSvc() throws AssertionFailedError, Exception { 
		// First let's store the Customer
		assertTrue(ics.storeCustomer(c));

		// Then let's read it back in
		c = ics.getCustomer(c.getId());
		assertTrue(c.validate());

		// Update the Customer
		c.setLastName("Smith");
		assertTrue(ics.storeCustomer(c));

		// Finally, let's cleanup the file that was created
		assertTrue(ics.deleteCustomer(c.getId()));
	}

	/**
	 * Unit Tests for invalid Customer get
	 * @throws AssertionFailedError
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void testInvalidGetCustomer() throws AssertionFailedError, NumberFormatException, Exception {
		c = ics.getCustomer(new Integer("1234"));

		if(c != null)
			assertFalse(c.validate());
	}
}
