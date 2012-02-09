/**
 * 
 */
package com.burritopos.service.test;

//import java.util.UUID;
//import org.apache.log4j.*;
//import java.util.Date;

import com.burritopos.domain.Customer;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import com.burritopos.service.Factory;
import com.burritopos.service.ICustomerSvc;

/**
 * @author james.bloom
 *
 */
public class CustomerSvcImplTestCase extends TestCase {
	private Factory factory;
	private Customer c;
	//private static Logger dLog = Logger.getLogger(CustomerSvcImplTestCase.class);

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		factory = Factory.getInstance();
		c = new Customer(new Integer("1"),"Jim","Bloom","jim@gmail.com");
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Unit Tests for Customer service
	 */
	public void testCustomerSvc() throws AssertionFailedError {
		try {
			//week 3
			//ICustomerSvc ics = factory.getCustomerSvc();

			//week 4
			ICustomerSvc ics = (ICustomerSvc) factory.getService(ICustomerSvc.NAME); 

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
		catch(Exception e) {
			System.out.println("Exception in testStoreCustomer: " + e.getMessage());
			fail(e.getMessage());
		}
	}

	public void testInvalidGetCustomer() throws AssertionFailedError {
		try {
			//week 3
			//ICustomerSvc ics = factory.getCustomerSvc();

			//week 4
			ICustomerSvc ics = (ICustomerSvc) factory.getService(ICustomerSvc.NAME);
			c = ics.getCustomer(new Integer("1234"));

			if(c != null)
				assertFalse(c.validate());
		}
		catch(Exception e) {
			System.out.println("Exception in testInvalidGetCustomer: " + e.getMessage());
			fail(e.getMessage());			
		}
	}
}
